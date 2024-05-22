window.onload = function() {
    sessionStorage.removeItem("productoId");

    let categorias = [];
    
    let btnsEditar = document.querySelectorAll(".btn-editar");

    const inputFiltro = document.querySelector(".input-search")
    const botonBuscarId = document.querySelector(".button-search-id")
    const botonBuscarNombre = document.querySelector(".button-search-nombre")
    const containerCategorias = document.querySelector(".contenedor-categorias");

    const url = 'http://localhost:8081/Apiweb/v1/producto/';
    fetch(url)
    .then(response => response.json())
    .then(data => {
        categorias = data;
        mostrarCategorias(categorias);
        });

    function mostrarCategorias(categorias) {
        containerCategorias.innerHTML = "";

        const trHeader = document.createElement("tr");
        trHeader.innerHTML = `
            <th>ID</th>
            <th>Nombre</th>
            <th>Descripción</th>
            <th>Id subcategorias</th>
            <th>Editar</th>
        `;
        containerCategorias.appendChild(trHeader);

        categorias.forEach(categoria => {
            const tr = document.createElement("tr");
            tr.innerHTML = `
            <td>${categoria.idCategoria}</td>
            <td>${categoria.nombre}</td>
            <td>${categoria.descripcion}</td>
            <td>${categoria.subIdCategoria}</td>
            <td><button class="btn-editar" id="${categoria.idCategoria}">Editar</button></td>
            `;

            containerCategorias.appendChild(tr);
        });
        actualizarBotonesEditar();
    }    
    
    function actualizarBotonesEditar() {
        btnsEditar = document.querySelectorAll(".btn-editar");
    
        btnsEditar.forEach(boton => {
            boton.addEventListener("click", () => {
                sessionStorage.setItem("idCategoria", JSON.stringify(parseInt(boton.id)))
                window.location.href = "editarCategoria.html";
            });
        });
    }

    function addEventListeners() {
        botonBuscarId.addEventListener('click', function() {
            const idBuscado = parseInt(inputFiltro.value);
            const categoriasFiltradas = categorias.find(categoria => categoria.idCategoria === idBuscado);
        
            if (categoriasFiltradas) {
                mostrarCategorias([categoriasFiltradas]);
            } else {
                containerCategorias.innerHTML = "No se encontró ningún producto con ese ID.";
            }
        });

        botonBuscarNombre.addEventListener('click', function() {
            const nombreBuscado = inputFiltro.value.toLowerCase();
            let categoriasFiltradas = categorias.filter(categoria => categoria.nombre.toLowerCase() === nombreBuscado);

            if (categoriasFiltradas.length > 0) {
                mostrarCategorias(categoriasFiltradas);
            } else {
                containerCategorias.innerHTML = "No se encontró ningún producto con ese nombre";
            }
        });
    }
    addEventListeners();
}
