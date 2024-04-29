window.onload = function() {
    sessionStorage.removeItem("ordenId");

    let ordenes = [];

    let btnsDetalles = document.querySelectorAll(".btn-detalles");
    let btnsGestionar = document.querySelectorAll(".btn-gestionar");
    const botonEstado = document.querySelectorAll(".boton-estado");
    const aside = document.querySelector("aside");
    const inputFiltro = document.querySelector(".input-search")
    const botonBuscar = document.querySelector(".button-search")

    const url = 'http://localhost:8081/Apiweb/v1/orden/';
    fetch(url)
    .then(response => response.json())
    .then(data => {
        ordenes = data;
        mostrarOrdenes(ordenes);
        });

    function mostrarOrdenes(ordenes) {
        const containerOrdenes = document.querySelector(".contenedor-ordenes");
        containerOrdenes.innerHTML = "";

        const trHeader = document.createElement("tr");
        trHeader.innerHTML = `
            <th>Nombre</th>
            <th>Género</th>
            <th>Estado</th>
            <th>Productos</th>
            <th>Gestionar informacion</th>
        `;
        containerOrdenes.appendChild(trHeader);

        ordenes.forEach(orden => {
            const tr = document.createElement("tr");
            tr.innerHTML = `
            <td>${orden.idOrden}</td>
            <td>${orden.cedula.nombre}</td>
            <td>${orden.estado}</td>
            <td><button class="btn-detalles" id="${orden.idOrden}">Ver productos</button></td>
            <td><button class="btn-gestionar" id="${orden.idOrden}">Gestionar</button></td>
            `;

            containerOrdenes.appendChild(tr);
        });
        actualizarBotonesDetalles();
        actualizarBotonesGestionar();
    }    

    function actualizarBotonesDetalles() {
        btnsDetalles = document.querySelectorAll(".btn-detalles");
    
        btnsDetalles.forEach(boton => {
            boton.addEventListener("click", cargarProductosOrden);
        });
    }
    
    function actualizarBotonesGestionar() {
        btnsGestionar = document.querySelectorAll(".btn-gestionar");
    
        btnsGestionar.forEach(boton => {
            boton.addEventListener("click", () => {
                sessionStorage.setItem("ordenId", JSON.stringify(parseInt(boton.id)))
                window.location.href = "gestionarOrdenEncargado.html";
            });
        });
    }

    function cargarProductosOrden(e){
        const url = `http://localhost:8081/Apiweb/v1/ordenProd/visualizarProductos/${e.currentTarget.id}`
        fetch(url)
        .then(response => response.json())
        .then(data => {
            ordenProd = data;
            console.log(ordenProd);
            mostrarProductosOrden(ordenProd);
        })
    }

    function mostrarProductosOrden(ordenProd){
        const containerProductos = document.querySelector(".contenedor-productos");
        containerProductos.innerHTML = "";
        ordenProd.forEach(producto => {
            const tr = document.createElement("tr");
            tr.innerHTML = `
            <td>${producto.idProducto.nombre}</td>
            <td>${producto.idProducto.genero}</td>
            <td>${producto.idProducto.estado}</td>
            <td>${producto.idProducto.personalizable}</td>
            `;

            containerProductos.appendChild(tr);
        });
    }

    function addEventListeners() {
        botonEstado.forEach(boton => boton.addEventListener("click", handleEstadoClick));
        botonBuscar.addEventListener("click", () => filtrarOrdenesPorId(parseInt(inputFiltro.value)));
    }

    function handleEstadoClick(e) {
        aside.classList.remove("aside-visible");
        botonEstado.forEach(boton => boton.classList.remove("active"));
        e.currentTarget.classList.add("active");
        estadoActivo = e.currentTarget.id;

        if (estadoActivo !== "todos") {
        const ordenesEstado = ordenes.filter(orden => orden.estado === estadoActivo);
        mostrarOrdenes(ordenesEstado);
        } else {
            mostrarOrdenes(ordenes);
        }
    }

    function filtrarOrdenesPorId(ordenId) {
        const url = `http://localhost:8081/Apiweb/v1/orden/${ordenId}`;
        fetch(url)
        .then(response => response.json())
        .then(data => {
            ordenes = data;
            mostrarOrdenes(ordenes);
        });
    }

    addEventListeners();
}

