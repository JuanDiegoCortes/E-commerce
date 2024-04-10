let ordenes = [];

cedula = 582347196 /* localStorage.getItem("cedula"); */
url = `http://localhost:8081/Apiweb/v1/orden/visualizarOrdenes/`;

function fetchData(cedula) {
    return fetch(url + cedula)
        .then(response => response.json())
        .then(data => {
            ordenes = data;
            console.log(ordenes);
        })
        .catch(error => {
            console.error("Error al obtener los datos:", error);
        });
}
fetchData(cedula);