let generoActivo = ""; // Variable para almacenar el género activo
let productos = [];

fetch("http://localhost:8080/Apiweb/v1/producto/")
    .then(response => response.json())
    .then(data => {
        productos = data;
        cargarProductos(productos);
    })

const contenedorProductos = document.querySelector("#contenedor-productos");
const botonesGeneros = document.querySelectorAll(".boton-genero");
const botonCategorias = document.querySelectorAll(".boton-categoria");
const tituloPrincipal = document.querySelector("#titulo-principal");
let botonesAgregar = document.querySelectorAll(".producto-agregar");
const numerito = document.querySelector("#numerito");

botonesGeneros.forEach(boton => boton.addEventListener("click", () => {
  aside.classList.remove("aside-visible");
}))

botonCategorias.forEach(boton => boton.addEventListener("click", () => {
  aside.classList.remove("aside-visible");
}))

function cargarProductos(productosElegidos) {

  contenedorProductos.innerHTML = "";

  // Aplicar filtro del género activo si está definido y no es "todos"
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
              <p>${producto.descripcion}</p>
              <p class="producto-precio">Precio: ${producto.precio}</p>
              <button class="producto-agregar" id="${producto.id}">Agregar</button>
          </div>
      `;

      contenedorProductos.append(div);
  })

  actualizarBotonesAgregar();
}

botonesGeneros.forEach(boton => {
  boton.addEventListener("click", (e) => {

      botonesGeneros.forEach(boton => boton.classList.remove("active"));
      e.currentTarget.classList.add("active");

      // Actualizar género activo
      generoActivo = e.currentTarget.id;

      if (e.currentTarget.id != "todos") {
          const productoGenero = productos.filter(producto => producto.genero === e.currentTarget.id);
          tituloPrincipal.innerText = e.currentTarget.innerText;
          cargarProductos(productoGenero);
      } else {
          tituloPrincipal.innerText = "Todos los productos";
          cargarProductos(productos);
      }

  })
});

botonCategorias.forEach(boton => {
  boton.addEventListener("click", (e) => {
    botonCategorias.forEach(boton => boton.classList.remove("active"));
    e.currentTarget.classList.add("active");

    const categoriaId = e.currentTarget.id; // Obtén el ID de la categoría seleccionada

    if (categoriaId !== "1") {
      // Filtra los productos por el ID de categoría seleccionado
      const productosFiltrados = productos.filter(producto => producto.idCategoria === categoriaId);
      tituloPrincipal.innerText = e.currentTarget.innerText; // Actualiza el título con el nombre de la categoría seleccionada
      cargarProductos(productosFiltrados); // Carga los productos filtrados en el contenedor
    } else {
      // Si se selecciona "Todo", muestra todos los productos
      tituloPrincipal.innerText = "Todos los productos";
      cargarProductos(productos); // Carga todos los productos en el contenedor
    }
  });
});



function actualizarBotonesAgregar() {
  botonesAgregar = document.querySelectorAll(".producto-agregar");

  botonesAgregar.forEach(boton => {
      boton.addEventListener("click", agregarAlCarrito);
  });
}

let productosEnCarrito;

let productosEnCarritoLS = localStorage.getItem("productos-en-carrito");

if (productosEnCarritoLS) {
  productosEnCarrito = JSON.parse(productosEnCarritoLS);
  actualizarNumerito();
} else {
  productosEnCarrito = [];
}

function agregarAlCarrito(e) {

  Toastify({
      text: "Producto agregado",
      duration: 3000,
      close: true,
      gravity: "top", // `top` or `bottom`
      position: "right", // `left`, `center` or `right`
      stopOnFocus: true, // Prevents dismissing of toast on hover
      style: {
        background: "linear-gradient(to right, #4b33a8, #785ce9)",
        borderRadius: "2rem",
        textTransform: "uppercase",
        fontSize: ".75rem"
      },
      offset: {
          x: '1.5rem', // horizontal axis - can be a number or a string indicating unity. eg: '2em'
          y: '1.5rem' // vertical axis - can be a number or a string indicating unity. eg: '2em'
        },
      onClick: function(){} // Callback after click
    }).showToast();

  const idBoton = e.currentTarget.id;
  const productoAgregado = productos.find(producto => producto.id === idBoton);

  if(productosEnCarrito.some(producto => producto.id === idBoton)) {
      const index = productosEnCarrito.findIndex(producto => producto.id === idBoton);
      productosEnCarrito[index].cantidad++;
  } else {
      productoAgregado.cantidad = 1;
      productosEnCarrito.push(productoAgregado);
  }

  actualizarNumerito();

  localStorage.setItem("productos-en-carrito", JSON.stringify(productosEnCarrito));
}

function actualizarNumerito() {
  let nuevoNumerito = productosEnCarrito.reduce((acc, producto) => acc + producto.cantidad, 0);
  numerito.innerText = nuevoNumerito;
}

document.getElementById("boton-ropa-personalizacion").addEventListener("click", function() {
  var dropdownContent = document.getElementById("contenido-personalizacion");
  if (dropdownContent.style.display === "block") {
    dropdownContent.style.display = "none";
  } else {
    dropdownContent.style.display = "block";
  }
});

document.getElementById("boton-ropa-sin-personalizacion").addEventListener("click", function() {
  var dropdownContent = document.getElementById("contenido-sin-personalizacion");
  if (dropdownContent.style.display === "block") {
    dropdownContent.style.display = "none";
  } else {
    dropdownContent.style.display = "block";
  }
});
