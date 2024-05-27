window.onload = function() {

    let categorias = [];
    
    let btnsEditar = document.querySelectorAll(".btn-editar");
    // Select DOM elements
    const inputFiltro = document.querySelector(".input-search")
    const botonBuscarId = document.querySelector(".button-search-id")
    const botonBuscarNombre = document.querySelector(".button-search-nombre")
    const containerCategorias = document.querySelector(".contenedor-categorias");

    const btnCrear = document.querySelector(".btnCrear")
    btnCrear.addEventListener("click", crearCategoria);

    function fetchData() {
        const url = "http://localhost:8081/Apiweb/v1/categoria/";
        return fetch(url)
          .then(response => response.json())
          .then(data => {
            categorias = data;
            mostrarCategorias(categorias);
          });
      }

    function mostrarCategorias(categorias) {
        containerCategorias.innerHTML = "";

        const trHeader = document.createElement("tr");
        trHeader.innerHTML = `
            <th>ID</th>
            <th>Nombre</th>
            <th>Descripción</th>
            <th>Id subCategorias</th>
            <th>Editar</th>
        `;
        containerCategorias.appendChild(trHeader);

        categorias.forEach(categoria => {
            const tr = document.createElement("tr");
            tr.innerHTML = `
            <td>${categoria.idCategoria}</td>
            <td>${categoria.nombre}</td>
            <td>${categoria.descripcion}</td>
            <td>${categoria.subIdCategoria ? categoria.subIdCategoria.idCategoria : 'N/A'}</td>
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
                sessionStorage.setItem("categoriaId", JSON.stringify(parseInt(boton.id)))
                window.location.href = "editarCategoria.html";
            });
        });
    }

    function addEventListeners() {
        botonBuscarId.addEventListener('click', function() {
            const idBuscado = parseInt(inputFiltro.value);
            const categoriaFiltrada = categorias.find(categoria => categoria.idCategoria === idBuscado);
        
            if (categoriaFiltrada) {
                mostrarCategorias([categoriaFiltrada]);
            } else {
                containerCategorias.innerHTML = "No se encontró ninguna categoria con ese ID.";
            }
        });

        botonBuscarNombre.addEventListener('click', function() {
            const nombreBuscado = inputFiltro.value.toLowerCase();
            let categoriasFiltradas = categorias.filter(categoria => categoria.nombre.toLowerCase() === nombreBuscado);

            if (categoriasFiltradas.length > 0) {
                mostrarCategorias(categoriasFiltradas);
            } else {
                containerCategorias.innerHTML = "No se encontró ninguna categoria con ese nombre";
            }
        });
    }

    let idSubCategoria = null;

    document.querySelectorAll(".boton-subcategoria").forEach(boton => {
        boton.addEventListener("click", function() {
            idSubCategoria = this.id;
        });
    });

    function crearCategoria() {
        const idCategoria = document.querySelector("#id").value;
        const nombre = document.querySelector("#nombre").value;
        const descripcion = document.querySelector("#descripcion").value;

        const nuevaCategoria = {
            idCategoria: idCategoria,
            nombre: nombre,
            descripcion: descripcion,
            subIdCategoria: {idCategoria:idSubCategoria}
        }

        const url = "http://localhost:8081/Apiweb/v1/categoria/";
        fetch(url, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(nuevaCategoria)
        })
        .then(response => response.text().then(text => console.log(text)))
        .then(data => {
            fetchData();
        });
    }

    fetchData();
    addEventListeners();
}
