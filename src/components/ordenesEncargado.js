window.onload = function() {

    let ordenes = [];

    const url = 'http://localhost:8081/Apiweb/v1/orden/visualizarOrdenesConProductos';
    fetch(url)
    .then(response => response.json())
    .then(data => {
        ordenes = data;
        console.log(ordenes);
        cargarOrdenes(ordenes);
        });

    function cargarOrdenes(ordenes) {
        const containerOrdenes = document.querySelector(".contenedor-ordenes");
        containerOrdenes.innerHTML = "";
        ordenes.forEach(orden => {
            const tr = document.createElement("tr");
            tr.innerHTML = `
            <td>${orden.idOrden}</td>
            <td>${orden.cedula.nombre}</td>
            <td>${orden.estado}</td>
            `;

            containerOrdenes.appendChild(tr);
        });
    }

}