let ordenes = [];
cedula = 582347196 /* localStorage.getItem("cedula"); */

function fetchData(cedula) {
    return fetch(`http://localhost:8081/Apiweb/v1/orden/`) /* visualizarOrdenes/${cedula} */
        .then(response => response.json())
        .then(data => {
            ordenes = data;
            console.log(ordenes);
            /* ordenes.filter(orden => orden.cedula.cedula === cedula).map(orden => {
                console.log(orden);
            }); */
        })
        .catch(error => {
            console.error("Error al obtener los datos:", error);
        });
}
fetchData(cedula);

