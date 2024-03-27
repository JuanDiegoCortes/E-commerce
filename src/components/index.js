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
              <button class="producto-agregar" id="${producto.idProducto}">Agregar</button>
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
      const productosFiltrados = productos.filter(producto => producto.idCategoria.idCategoria === parseInt(categoriaId));
      tituloPrincipal.innerText = e.currentTarget.innerText; // Actualiza el título con el nombre de la categoría seleccionada
      cargarProductos(productosFiltrados); // Carga los productos filtrados en el contenedor
    } else {
      // Si se selecciona "Todo", muestra todos los productos
      tituloPrincipal.innerText = "Todos los productos";
      cargarProductos(productos); // Carga todos los productos en el contenedor
    }
  });
});



// Función para actualizar los eventos de click en los botones de agregar
function actualizarBotonesAgregar() {
  const botonesAgregar = document.querySelectorAll(".producto-agregar");

  botonesAgregar.forEach(boton => {
    boton.addEventListener("click", agregarAlCarrito);
  });
}

// Variable para almacenar los productos en el carrito
let productosEnCarrito;

// Obtenemos los productos en el carrito almacenados en localStorage, si existen
const productosEnCarritoLS = localStorage.getItem("productos-en-carrito");

try {
  // Intentamos parsear los productos en el carrito
  productosEnCarrito = productosEnCarritoLS ? JSON.parse(productosEnCarritoLS) : [];
  actualizarNumerito();
} catch (error) {
  console.error("Error al parsear los productos en el carrito:", error);
  // Si hay un error al parsear, asignamos un array vacío a productosEnCarrito
  productosEnCarrito = [];
}

// Función para agregar un producto al carrito
function agregarAlCarrito(e) {
  try {
    Toastify({
      text: "Producto agregado",
      duration: 1000,
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

    // Validamos que haya una lista de productos y un ID de botón válido
    if (productos && idBoton !== undefined) {
      console.log(idBoton)
      const productoAgregado = productos.find(producto => producto.idProducto === parseInt(idBoton));

      if (productoAgregado) {
        // Verificamos si el producto ya está en el carrito
        const index = productosEnCarrito.findIndex(producto => producto.idProducto === parseInt(idBoton));
        if (index !== -1) {
          productosEnCarrito[index].cantidad++;
        } else {
          productoAgregado.cantidad = 1;
          productosEnCarrito.push(productoAgregado);
        }

        // Actualizamos el número de elementos en el carrito
        actualizarNumerito();

        // Guardamos los cambios en localStorage
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

// Función para actualizar el número de elementos en el carrito
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

// Llamamos a la función para actualizar los botones de agregar al cargar la página
actualizarBotonesAgregar();
