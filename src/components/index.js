// Define variables
let generoActivo = "";
let productos = [];
let productosEnCarrito = [];

// Select DOM elements
const contenedorProductos = document.querySelector("#contenedor-productos");
const botonesGeneros = document.querySelectorAll(".boton-genero");
const botonCategorias = document.querySelectorAll(".boton-categoria");
const botonPersonalizable = document.querySelectorAll(".boton-personalizable");
const tituloPrincipal = document.querySelector("#titulo-principal");
const numerito = document.querySelector("#numerito");

// Fetch data from API
function fetchData() {
  const url = "http://localhost:8081/Apiweb/v1/producto/";
  return fetch(url)
    .then(response => response.json())
    .then(data => {
      productos = data;
      console.log(productos);
      cargarProductos(productos);
      productosEnCarrito = JSON.parse(localStorage.getItem("productos-en-carrito")) || [];
      actualizarNumerito();
    });
}

// Add event listeners
function addEventListeners() {
  botonesGeneros.forEach(boton => boton.addEventListener("click", handleGeneroClick));
  botonCategorias.forEach(boton => boton.addEventListener("click", handleCategoriaClick));
  botonPersonalizable.forEach(boton => boton.addEventListener("click", handlePersonalizableClick));
}

// Handle genero click event
function handleGeneroClick(e) {
  aside.classList.remove("aside-visible");
  botonesGeneros.forEach(boton => boton.classList.remove("active"));
  e.currentTarget.classList.add("active");
  generoActivo = e.currentTarget.id;

  if (generoActivo !== "todos") {
    const productoGenero = productos.filter(producto => producto.genero === generoActivo);
    tituloPrincipal.innerText = e.currentTarget.innerText;
    cargarProductos(productoGenero);
  } else {
    tituloPrincipal.innerText = "Todos los productos";
    cargarProductos(productos);
  }
}

// Handle categoria click event
function handleCategoriaClick(e) {
  aside.classList.remove("aside-visible");
  botonCategorias.forEach(boton => boton.classList.remove("active"));
  e.currentTarget.classList.add("active");
  const categoriaId = e.currentTarget.id;

  if (categoriaId !== "1") {
    const productosFiltrados = productos.filter(producto => producto.idCategoria.idCategoria === parseInt(categoriaId));
    tituloPrincipal.innerText = e.currentTarget.innerText;
    cargarProductos(productosFiltrados);
  } else {
    tituloPrincipal.innerText = "Todos los productos";
    cargarProductos(productos);
  }
}

// Handle personalizable click event
function handlePersonalizableClick(e) {
  aside.classList.remove("aside-visible");
  botonPersonalizable.forEach(boton => boton.classList.remove("active"));
  e.currentTarget.classList.add("active");
  const personalizable = e.currentTarget.id;

  let productosFiltrados = productos;

  if (generoActivo && generoActivo !== "todos") {
    productosFiltrados = productosFiltrados.filter(producto => producto.genero === generoActivo);
  }

  const categoriaId = document.querySelector(".boton-categoria.active")?.id;
  if (categoriaId && categoriaId !== "1") {
    productosFiltrados = productosFiltrados.filter(producto => producto.idCategoria.idCategoria === parseInt(categoriaId));
  }

  const productosPersonalizables = productosFiltrados.filter(producto => producto.personalizable === personalizable);
  cargarProductos(productosPersonalizables);
}


// Function to load products
function cargarProductos(productosElegidos) {
  contenedorProductos.innerHTML = "";

  if (generoActivo && generoActivo !== "todos") {
    productosElegidos = productosElegidos.filter(producto => producto.genero === generoActivo);
  }

  productosElegidos.forEach(producto => {
    const div = document.createElement("div");
    div.classList.add("producto");
    div.innerHTML = `
      <img class="producto-imagen" src="${producto.image_Url}" alt="${producto.nombre}">
      <div class="producto-detalles">
        <h3 class="producto-titulo">${producto.nombre}</h3>
        <p class="producto-personalizable">Personalizable: ${producto.personalizable}</p>
        <p class="producto-precio">Precio: ${producto.precio}</p>
        <button class="producto-agregar" id="${producto.idProducto}">Agregar</button>
        <button class="producto-info" id="${producto.idProducto}">Informacion</button>
      </div>
      `;
    contenedorProductos.append(div);

    const botonInformacion = div.querySelector(".producto-info");
    botonInformacion.addEventListener("click", function() {
      sessionStorage.setItem("productoSeleccionado", JSON.stringify(producto));
      window.location.href = "../pages/infoProducto.html";
    });
  });

  actualizarBotonesAgregar();
}

// Function to update click events on "Agregar" buttons
function actualizarBotonesAgregar() {
  const botonesAgregar = document.querySelectorAll(".producto-agregar");
  botonesAgregar.forEach(boton => {
    boton.addEventListener("click", agregarAlCarrito);
  });
}

// Function to add product to cart
function agregarAlCarrito(e) {
  try {
    Toastify({
      text: "Producto agregado",
      duration: 1000,
      close: true,
      gravity: "top",
      position: "right",
      stopOnFocus: true,
      style: {
        background: "linear-gradient(to right, #4b33a8, #785ce9)",
        borderRadius: "2rem",
        textTransform: "uppercase",
        fontSize: ".75rem"
      },
      offset: {
        x: '1.5rem',
        y: '1.5rem'
      },
      onClick: function(){}
    }).showToast();

    const idBoton = e.currentTarget.id;

    if (productos && idBoton !== undefined) {
      const productoAgregado = productos.find(producto => producto.idProducto === parseInt(idBoton));

      if (productoAgregado) {
        const index = productosEnCarrito.findIndex(producto => producto.idProducto === parseInt(idBoton));
        if (index !== -1) {
          productosEnCarrito[index].cantidad++;
        } else {
          productoAgregado.cantidad = 1;
          productosEnCarrito.push(productoAgregado);
        }

        actualizarNumerito();
        localStorage.setItem("productos-en-carrito", JSON.stringify(productosEnCarrito));
      } else {
        console.error("El producto no se encontró en la lista de productos.");
      }
    } else {
      console.error("No se pudo agregar el producto al carrito debido a datos faltantes.");
    }
  } catch (error) {
    console.error("Error al agregar producto al carrito:", error);
  }
}

// Function to update the cart item count
function actualizarNumerito() {
  try {
    const numerito = document.getElementById("numerito");
    if (numerito) {
      const nuevoNumerito = productosEnCarrito.reduce((acc, producto) => acc + producto.cantidad, 0);
      numerito.innerText = nuevoNumerito;
    } else {
      console.warn("El elemento con id 'numerito' no fue encontrado en el DOM.");
    }
  } catch (error) {
    console.error("Error al actualizar el numerito:", error);
  }
}

// Call the functions to fetch data and add event listeners on page load
fetchData();
addEventListeners();
