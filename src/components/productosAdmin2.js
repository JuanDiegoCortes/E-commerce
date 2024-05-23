window.onload = function() {

    let productos = [];
    
    let btnsEditar = document.querySelectorAll(".btn-editar");
    // Select DOM elements
    const inputFiltro = document.querySelector(".input-search")
    const botonBuscarID = document.querySelector(".button-search-id")
    const botonBuscarNombre = document.querySelector(".button-search-nombre")
    const containerProductos = document.querySelector(".contenedor-productos");
    const botonCategorias = document.querySelectorAll(".boton-Categorias");

    function fetchData() {
        const url = "http://localhost:8081/Apiweb/v1/producto/";
        return fetch(url)
          .then(response => response.json())
          .then(data => {
            productos = data;
            console.log(productos);
            mostrarProductos(productos);
          });
      }

    function mostrarProductos(productos) {
        containerProductos.innerHTML = "";

        const trHeader = document.createElement("tr");
        trHeader.innerHTML = `
            <th>Imagen</th>
            <th>ID</th>
            <th>Nombre</th>
            <th>Descripción</th>
            <th>Precio</th>
            <th>Estado</th>
            <th>Personalizable</th>
            <th>Género</th>
            <th>Categoría</th>
            <th>Editar</th>
        `;
        containerProductos.appendChild(trHeader);

        productos.forEach(producto => {
            const tr = document.createElement("tr");
            tr.innerHTML = `
            <td><img class="producto-img" src="${producto.image_Url}" alt="${producto.nombre}"></td>
            <td>${producto.idProducto}</td>
            <td>${producto.nombre}</td>
            <td>${producto.descripcion}</td>
            <td>${producto.precio}</td>
            <td>${producto.estado}</td>
            <td>${producto.personalizable}</td>  
            <td>${producto.genero}</td>
            <td>${producto.idCategoria.nombre}</td>
            <td><button class="btn-editar" id="${producto.idProducto}">Editar</button></td>
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

        botonCategorias.forEach(boton => boton.addEventListener("click", function(e) {
            botonCategorias.forEach(boton => boton.classList.remove("active"));
            e.currentTarget.classList.add("active");

            let categoriaActiva = e.currentTarget.id;
        
            if (categoriaActiva !== "todos") {
                const productosCategoria = productos.filter(producto => producto.idCategoria.nombre === categoriaActiva);
                if (productosCategoria.length === 0) {
                    containerProductos.innerHTML = "No hay productos en esa categoría.";
                } else {
                    mostrarProductos(productosCategoria);
                }
            } else {
                mostrarProductos(productos);
            }
        }));

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
            let productosFiltrados = productos.filter(producto => producto.nombre.toLowerCase().includes(nombreBuscado));
            if (productosFiltrados.length > 0) {
                mostrarProductos(productosFiltrados);
            } else {
                containerProductos.innerHTML = "No se encontró ningún producto con ese nombre";
            }
        });
        
    }
    fetchData();
    addEventListeners();
}
