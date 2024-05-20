window.onload = function() {
    sessionStorage.removeItem("productoId");

    let productos = [];
    
    let btnsEditar = document.querySelectorAll(".btn-editar");

    const inputFiltro = document.querySelector(".input-search")
    const botonBuscarID = document.querySelector(".button-search-id")
    const botonBuscarNombre = document.querySelector(".button-search-nombre")
    const containerProductos = document.querySelector(".contenedor-productos");

    const url = 'http://localhost:8081/Apiweb/v1/producto/';
    fetch(url)
    .then(response => response.json())
    .then(data => {
        productos = data;
        mostrarProductos(productos);
        });

    function mostrarProductos(productos) {
        containerProductos.innerHTML = "";

        const trHeader = document.createElement("tr");
        trHeader.innerHTML = `
            <th>Imagen</th>
            <th>Nro Articulo</th>
            <th>Nombre</th>
            <th>Descripción</th>
            <th>Precio</th>
            <th>Estado</th>
            <th>Personalizable</th>
            <th>Género</th>
            <th>Categoría</th>
            <th>Tallas</th>
            <th>Editar Articulo</th>
        `;
        containerProductos.appendChild(trHeader);

        productos.forEach(producto => {
            const tr = document.createElement("tr");
            let tallas = producto.idProdTalla.map(talla => talla.idTalla).join(", ");
            tr.innerHTML = `
            <td><img src="${producto.image_Url}" alt="${producto.nombre}"></td>
            <td>${producto.idProducto}</td>
            <td>${producto.nombre}</td>
            <td>${producto.descripcion}</td>
            <td>${producto.precio}</td>
            <td>${producto.estado}</td>
            <td>${producto.personalizable}</td>  
            <td>${producto.genero}</td>
            <td>${producto.idCategoria.nombre}</td>
            <td>${tallas}</td>
            <td><button class="btn-gestionar" id="${producto.idProducto}">Editar</button></td>
            `;

            containerProductos.appendChild(tr);
        });
        actualizarBotonesEditar();
    }    
    
    function actualizarBotonesEditar() {
        btnsEditar = document.querySelectorAll(".btn-editar");
    
        btnsEditar.forEach(boton => {
            boton.addEventListener("click", () => {
                sessionStorage.setItem("productoId", JSON.stringify(parseInt(boton.id)))
                window.location.href = "editarProducto.html";
            });
        });
    }

    function addEventListeners() {
        botonBuscarID.addEventListener('click', function() {
            const idBuscado = parseInt(inputFiltro.value);
            const productoFiltrado = productos.find(producto => producto.idProducto === idBuscado);
        
            if (productoFiltrado) {
                mostrarProductos([productoFiltrado]);
            } else {
                containerProductos.innerHTML = "No se encontró ningún producto con ese ID.";
            }
        });

        botonBuscarNombre.addEventListener('click', function() {
            const nombreBuscado = inputFiltro.value.toLowerCase();
            let productosFiltrados = productos.filter(producto => producto.nombre.toLowerCase() === nombreBuscado);

            if (productosFiltrados.length > 0) {
                mostrarProductos(productosFiltrados);
            } else {
                containerProductos.innerHTML = "No se encontró ningún producto con ese nombre";
            }
        });
    }
    addEventListeners();
}
