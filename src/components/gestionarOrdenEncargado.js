window.onload = function() {
    let ordenId = sessionStorage.getItem("ordenId");

    url = `http://localhost:8081/Apiweb/v1/orden/${ordenId}`;
    fetch(url)
    .then(response => response.json())
    .then(data => {
        console.log(data);
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
        `;
    
        table.appendChild(tr);
        containerOrdenes.appendChild(table);
    
        cargarProductosOrden(ordenId)
    }

    function cargarProductosOrden(ordenId){
        const url = `http://localhost:8081/Apiweb/v1/ordenProd/visualizarProductos/${ordenId}`
        fetch(url)
        .then(response => response.json())
        .then(data => {
            ordenProd = data;
            console.log(ordenProd);
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
}

