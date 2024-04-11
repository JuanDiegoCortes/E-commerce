document.addEventListener("DOMContentLoaded", (event) => {
    //Variables
    let ordenes = [];
    let ordenesListadas = []
    let cedula = 582347196 /* localStorage.getItem("usuario.cedula"); */

    //Select DOM elements
    const contenedorOrdenes = document.querySelector("#contenedor-orden");

    const url = `http://localhost:8081/Apiweb/v1/ordenProducto/visualizarOrdenes/${cedula}`;
    fetch(url)
        .then(response => response.json())
        .then(data => {
            ordenes = data;
            cargarHistorialOrdenesPorCedula(ordenes);
            console.log(ordenes);

        })
        .catch(error => {
            console.error("Error al obtener los datos:", error);
        });

    function cargarHistorialOrdenesPorCedula(ordenesSeleccionadas) {
        contenedorOrdenes.innerHTML = "";

        ordenesSeleccionadas.forEach(orden => {
            const div = document.createElement("div");
            div.classList.add("orden");
            div.innerHTML = `
            <div class="historial-Orden-atributos">
                <p>${orden.cedula.nombre}</p>
                <p>${orden.cedula.correo}</p>
                <p>${orden.fecha}</p>
                <p>${orden.estado}</p>
                <p>${orden.precioTotal}</p>
            </div>
            <div class="boton-info">
                <a href="../pages/ordenDetalle.html">
                    <button class="regresarBtn" > Info </button>
                </a>
            </div>
        `;
            contenedorOrdenes.appendChild(div);
            sessionStorage.setItem("ordenSeleccionada", JSON.stringify(orden));
            window.location.href = "../pages/ordenDetalle.html";
        });
    }
    localStorage.setItem("ordenesListadas", JSON.stringify(ordenesListadas));

});
