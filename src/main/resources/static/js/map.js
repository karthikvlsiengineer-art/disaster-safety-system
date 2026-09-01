let map;

let routeLayer;

let startMarker;

let destinationMarker;

let currentLatitude = null;

let currentLongitude = null;


// -----------------------------
// Initialize OpenStreetMap
// -----------------------------

document.addEventListener(
    "DOMContentLoaded",
    function () {

        map = L.map("map").setView(
            [20.5937, 78.9629],
            5
        );

        L.tileLayer(
            "https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png",
            {
                maxZoom: 19,

                attribution:
                    "&copy; OpenStreetMap contributors"
            }
        ).addTo(map);
    }
);


// -----------------------------
// Current location
// -----------------------------

function useMyLocation() {

    if (!navigator.geolocation) {

        alert(
            "Location is not supported by your browser."
        );

        return;
    }


    navigator.geolocation.getCurrentPosition(

        function (position) {

            currentLatitude =
                position.coords.latitude;

            currentLongitude =
                position.coords.longitude;


            document.getElementById(
                "origin"
            ).value =
                currentLatitude
                + ","
                + currentLongitude;


            if (startMarker) {

                map.removeLayer(
                    startMarker
                );
            }


            startMarker =
                L.marker([
                    currentLatitude,
                    currentLongitude
                ])
                .addTo(map)
                .bindPopup(
                    "Your Current Location"
                )
                .openPopup();


            map.setView(
                [
                    currentLatitude,
                    currentLongitude
                ],
                13
            );
        },

        function () {

            alert(
                "Unable to access your current location."
            );
        }
    );
}


// -----------------------------
// Convert place name to coordinates
// -----------------------------

async function geocodeLocation(
    locationName
) {

    // Allow coordinates directly

    if (
        locationName.includes(",")
    ) {

        const parts =
            locationName.split(",");


        const lat =
            parseFloat(
                parts[0]
            );


        const lon =
            parseFloat(
                parts[1]
            );


        if (
            !isNaN(lat)
            && !isNaN(lon)
        ) {

            return {
                lat: lat,
                lon: lon
            };
        }
    }


    const url =
        "https://nominatim.openstreetmap.org/search"
        + "?format=json"
        + "&limit=1"
        + "&q="
        + encodeURIComponent(
            locationName
        );


    const response =
        await fetch(
            url
        );


    const data =
        await response.json();


    if (
        !data
        || data.length === 0
    ) {

        throw new Error(
            "Location not found"
        );
    }


    return {

        lat:
            parseFloat(
                data[0].lat
            ),

        lon:
            parseFloat(
                data[0].lon
            )
    };
}


// -----------------------------
// Find route
// -----------------------------

async function findSafeRoute() {

    const originText =
        document
            .getElementById(
                "origin"
            )
            .value
            .trim();


    const destinationText =
        document
            .getElementById(
                "destination"
            )
            .value
            .trim();


    const resultBox =
        document
            .getElementById(
                "routeResults"
            );


    if (
        originText === ""
        || destinationText === ""
    ) {

        alert(
            "Enter both starting location and destination."
        );

        return;
    }


    resultBox.innerHTML =
        "Finding route and checking weather risk...";


    try {

        const start =
            await geocodeLocation(
                originText
            );


        const destination =
            await geocodeLocation(
                destinationText
            );


        // -----------------------------
        // OSRM Routing API
        // -----------------------------

        const routeURL =
            "https://router.project-osrm.org/route/v1/driving/"
            + start.lon
            + ","
            + start.lat
            + ";"
            + destination.lon
            + ","
            + destination.lat
            + "?overview=full"
            + "&geometries=geojson"
            + "&alternatives=true";


        const response =
            await fetch(
                routeURL
            );


        const data =
            await response.json();


        if (
            !data.routes
            || data.routes.length === 0
        ) {

            throw new Error(
                "No route found"
            );
        }


        if (routeLayer) {

            map.removeLayer(
                routeLayer
            );
        }


        if (startMarker) {

            map.removeLayer(
                startMarker
            );
        }


        if (destinationMarker) {

            map.removeLayer(
                destinationMarker
            );
        }


        startMarker =
            L.marker([
                start.lat,
                start.lon
            ])
            .addTo(map)
            .bindPopup(
                "Starting Location"
            );


        destinationMarker =
            L.marker([
                destination.lat,
                destination.lon
            ])
            .addTo(map)
            .bindPopup(
                "Destination"
            );


        const bestRoute =
            data.routes[0];


        const coordinates =
            bestRoute.geometry.coordinates.map(

                function (point) {

                    return [
                        point[1],
                        point[0]
                    ];
                }
            );


        routeLayer =
            L.polyline(
                coordinates,
                {
                    weight: 6
                }
            )
            .addTo(map);


        map.fitBounds(
            routeLayer.getBounds()
        );


        const distance =
            (
                bestRoute.distance
                / 1000
            )
            .toFixed(2);


        const duration =
            Math.round(
                bestRoute.duration
                / 60
            );


        // -----------------------------
        // Check weather around route midpoint
        // -----------------------------

        const middleIndex =
            Math.floor(
                coordinates.length
                / 2
            );


        const middle =
            coordinates[
                middleIndex
            ];


        const riskResponse =
            await fetch(

                "/api/route-risk"
                + "?lat="
                + middle[0]
                + "&lng="
                + middle[1]
            );


        const risk =
            await riskResponse.json();


        let message =
            "<h2>Route Analysis</h2>";


        message +=

            "<p><strong>Distance:</strong> "
            + distance
            + " km</p>";


        message +=

            "<p><strong>Estimated Time:</strong> "
            + duration
            + " minutes</p>";


        message +=

            "<p><strong>Weather:</strong> "
            + risk.condition
            + "</p>";


        message +=

            "<p><strong>Risk Score:</strong> "
            + risk.riskScore
            + "/100</p>";


        message +=

            "<p><strong>Route Status:</strong> "
            + risk.status
            + "</p>";


        if (
            risk.riskScore >= 60
        ) {

            message +=

                "<p class='error'>"
                + "⚠ WARNING: Dangerous weather "
                + "conditions may exist along this route."
                + "</p>";

        } else if (
            risk.riskScore >= 30
        ) {

            message +=

                "<p class='warning'>"
                + "⚠ CAUTION: Some weather risk "
                + "has been detected."
                + "</p>";

        } else {

            message +=

                "<p>"
                + "✓ This route currently has "
                + "a lower calculated weather risk."
                + "</p>";
        }


        resultBox.innerHTML =
            message;


    } catch (error) {

        console.error(
            error
        );


        resultBox.innerHTML =

            "<p class='error'>"
            + "Unable to calculate route. "
            + "Check the place names and internet connection."
            + "</p>";
    }
}