window.onload = function() {
    let ordenId = sessionStorage.getItem("ordenId");

    url = `http://localhost:8081/Apiweb/v1/orden/${ordenId}`;
    fetch(url)
    .then(response => response.json())
    .then(data => {
        mostrarOrdenes(data);
        });

    function mostrarOrdenes(orden) {
        const containerOrdenes = document.querySelector(".contenedor-informacion");
        containerOrdenes.innerHTML = "";
    
        const table = document.createElement("table");
    
        const trHeader = document.createElement("tr");
        trHeader.innerHTML = `
            <th>Nro. Orden</th>
            <th>Cedula del Usuario</th>
            <th>Estado de la orden</th>
            <th>Evindecia de pago</th>
            <th>Metodo de pago</th>
            <th>Precio total</th>
            <th>Editar estado</th>
        `;
        table.appendChild(trHeader);
    
        if (!orden.image_evidencia) {
            validacionPago = "No se ha subido una evidencia de pago";
        } else {
            validacionPago = orden.image_evidencia;
        }
    
        const tr = document.createElement("tr");
        tr.innerHTML = `
        <td>${orden.idOrden}</td> 
        <td>${orden.cedula.cedula}</td>
        <td>${orden.estado}</td> 
        <td>${validacionPago}</td>
        <td>${orden.metodoPago}</td>
        <td>${orden.precioTotal}</td>
        <td>
            <div class="dropdown">
                <button id="${orden.idOrden}" class="btn btn-primary dropdown-toggle boton-editar-estado" type="button" data-bs-toggle="dropdown" aria-expanded="false">
                    Editar
                </button>
                <ul class="dropdown-menu">
                    <li><button id="activo" class="dropdown-item boton-estado" type="button">Activo</button></li>
                    <li><button id="inactivo" class="dropdown-item boton-estado" type="button">Inactivo</button></li>
                </ul>
            </div>
        </td>
        `;
    
        table.appendChild(tr);
        containerOrdenes.appendChild(table);
    
        cargarProductosOrden(ordenId);
        actualizarBotonesEditarEstado(); // Llama a esta función cada vez que se crea una nueva orden
    }

    function cargarProductosOrden(ordenId){
        const url = `http://localhost:8081/Apiweb/v1/ordenProd/visualizarProductos/${ordenId}`
        fetch(url)
        .then(response => response.json())
        .then(data => {
            ordenProd = data;
            mostrarProductosOrden(ordenProd);
        })
    }

    function mostrarProductosOrden(ordenProd){
        const containerProductos = document.querySelector(".contenedor-informacion-productos");
        containerProductos.innerHTML = "";

        const trHeader = document.createElement("tr");
        trHeader.innerHTML = `
            <th>Nombre</th>
            <th>Género</th>
            <th>Estado</th>
            <th>Personalizable</th>
            <th>Cantidad</th>
        `;
        containerProductos.appendChild(trHeader);

        ordenProd.forEach(producto => {
            const tr = document.createElement("tr");
            tr.innerHTML = `
            <td>${producto.idProducto.nombre}</td>
            <td>${producto.idProducto.genero}</td>
            <td>${producto.idProducto.estado}</td>
            <td>${producto.idProducto.personalizable}</td>
            <td>${producto.cantidad}</td>
            `;

            containerProductos.appendChild(tr);
        });
    }

    function actualizarBotonesEditarEstado() {
        const botonesEditarEstado = document.querySelectorAll(".boton-estado");
        botonesEditarEstado.forEach(boton => {
            boton.addEventListener("click", function(e) {
                const estado = e.target.id;
                actualizarEstadoOrden(ordenId, estado);
            });
        });
    }
    
    function actualizarEstadoOrden(ordenId, data) {
        const url = `http://localhost:8081/Apiweb/v1/orden/actualizarEstadoOrden/${ordenId}/${data}`;
        fetch(url, {
            method: 'PUT',
        })
        .then(response => response)
        .then(() => {
            Toastify({
                text: "Estado actualizado correctamente",
                duration: 1000,
                close: true,
                gravity: "top",
                position: "right",
                stopOnFocus: true,
                style: {
                    background: "linear-gradient(to right, #4b33a8, #785ce9)",
                    borderRadius: "2rem",
                }
            }).showToast();
            setTimeout(() => {
                location.reload();
            }, 1000);
        })
        .catch(error => console.error(error));
    }
}