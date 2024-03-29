let productosEnCarrito = localStorage.getItem("productos-en-carrito");
productosEnCarrito = JSON.parse(productosEnCarrito);

const contenedorCarritoVacio = document.querySelector("#carrito-vacio");
const contenedorCarritoProductos = document.querySelector("#carrito-productos");
const contenedorCarritoAcciones = document.querySelector("#carrito-acciones");
const contenedorCarritoComprado = document.querySelector("#carrito-comprado");
let botonesEliminar = document.querySelectorAll(".carrito-producto-eliminar");
let botonesPersonalizar = document.querySelectorAll(".carrito-producto-personalizacion");
const botonVaciar = document.querySelector("#carrito-acciones-vaciar");
const contenedorTotal = document.querySelector("#total");
const botonComprar = document.querySelector("#carrito-acciones-comprar");


function cargarProductosCarrito() {
    if (productosEnCarrito && productosEnCarrito.length > 0) {

        contenedorCarritoVacio.classList.add("disabled");
        contenedorCarritoProductos.classList.remove("disabled");
        contenedorCarritoAcciones.classList.remove("disabled");
        contenedorCarritoComprado.classList.add("disabled");
    
        contenedorCarritoProductos.innerHTML = "";
    
        productosEnCarrito.forEach(producto => {
    
            const div = document.createElement("div");
            div.classList.add("carrito-producto");
            div.innerHTML = `
                <img class="carrito-producto-imagen" src="${producto.image_Url}" alt="${producto.nombre}">
                <div class="carrito-producto-titulo">
                    <small>Título</small>
                    <h3>${producto.nombre}</h3>
                </div>
                <div class="carrito-producto-cantidad">
                    <small>Cantidad</small>
                    <p>${producto.cantidad}</p>
                </div>
                <div class="carrito-producto-precio">
                    <small>Precio</small>
                    <p>$${producto.precio}</p>
                </div>
                <div class="carrito-producto-subtotal">
                    <small>Subtotal</small>
                    <p>$${producto.precio * producto.cantidad}</p>
                </div>
                <button class="carrito-producto-personalizacion" id="${producto.idProducto}"><i class="bi bi-pencil-fill"></i></i></i></button> 
                <button class="carrito-producto-eliminar" id="${producto.idProducto}"><i class="bi bi-trash-fill"></i></button>
            `;
            //Hay que hacer el metodo de personalizacion para los productos, deberia ser una ventana distinta o que se agregue desde esa misma ventana un label de escritura.
            contenedorCarritoProductos.append(div);
        })
    
    actualizarBotonesEliminar();
    actualizarBotonesPersonalizar();
    actualizarTotal();
	
    } else {
        contenedorCarritoVacio.classList.remove("disabled");
        contenedorCarritoProductos.classList.add("disabled");
        contenedorCarritoAcciones.classList.add("disabled");
        contenedorCarritoComprado.classList.add("disabled");
    }

}

cargarProductosCarrito();

function actualizarBotonesPersonalizar() {
    botonesPersonalizar = document.querySelectorAll(".carrito-producto-personalizacion");

    botonesPersonalizar.forEach(boton => {
        boton.addEventListener("click", actualizarBotonesPersonalizar);
    });
}

function personalizarDelCarrito(e) {
    const idProducto = e.currentTarget.id; // Obtener el ID del producto

    // Verificar si existe información de personalización para este producto
    const productoPersonalizado = productosEnCarrito.find(producto => producto.idProducto === parseInt(idProducto));

    // Crear un formulario o área de entrada para la personalización
    const personalizacionForm = document.createElement("div");
    personalizacionForm.innerHTML = `
        <input type="file" id="personalizacion-imagen" accept="image/*">
        <textarea id="personalizacion-texto" placeholder="Ingrese su texto personalizado"></textarea>
        <button id="guardar-personalizacion">Guardar</button>
    `;

    // Mostrar la información de personalización existente si hay alguna
    if (productoPersonalizado && productoPersonalizado.personalizacion) {
        const { imagen, texto } = productoPersonalizado.personalizacion;
        if (imagen) {
            // Si hay una imagen guardada, mostrarla
            const imagenURL = URL.createObjectURL(imagen);
            personalizacionForm.querySelector("#personalizacion-imagen").setAttribute("disabled", true);
            personalizacionForm.querySelector("#personalizacion-imagen").insertAdjacentHTML("afterend", `<img src="${imagenURL}" alt="Imagen personalizada">`);
        }
        if (texto) {
            // Si hay texto guardado, mostrarlo
            personalizacionForm.querySelector("#personalizacion-texto").value = texto;
        }
    }

    // Agregar evento para guardar la personalización
    personalizacionForm.querySelector("#guardar-personalizacion").addEventListener("click", () => {
        // Obtener la imagen y el texto ingresados por el usuario
        const imagen = personalizacionForm.querySelector("#personalizacion-imagen").files[0];
        const texto = personalizacionForm.querySelector("#personalizacion-texto").value;

        // Actualizar el objeto del producto en el carrito con la personalización
        const index = productosEnCarrito.findIndex(producto => producto.idProducto === parseInt(idProducto));
        if (index !== -1) {
            productosEnCarrito[index].personalizacion = {
                imagen: imagen,
                texto: texto
            };
        }

        // Recargar los productos del carrito para reflejar los cambios
        cargarProductosCarrito();

        // Cerrar el formulario de personalización
        personalizacionForm.remove();
    });

    // Mostrar el formulario de personalización debajo del producto
    const productoDiv = e.currentTarget.closest(".carrito-producto");
    productoDiv.appendChild(personalizacionForm);
}

function actualizarBotonesEliminar() {
    botonesEliminar = document.querySelectorAll(".carrito-producto-eliminar");

    botonesEliminar.forEach(boton => {
        boton.addEventListener("click", eliminarDelCarrito);
    });
}

function eliminarDelCarrito(e) {
    Toastify({
        text: "Producto eliminado",
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
    const index = productosEnCarrito.findIndex(producto => producto.id === parseInt(idBoton));
    
    productosEnCarrito.splice(index, 1);
    cargarProductosCarrito();

    localStorage.setItem("productos-en-carrito", JSON.stringify(productosEnCarrito));

}

botonVaciar.addEventListener("click", vaciarCarrito);
function vaciarCarrito() {

    Swal.fire({
        title: '¿Estás seguro?',
        icon: 'question',
        html: `Se van a borrar ${productosEnCarrito.reduce((acc, producto) => acc + producto.cantidad, 0)} productos.`,
        showCancelButton: true,
        focusConfirm: false,
        confirmButtonText: 'Sí',
        cancelButtonText: 'No'
    }).then((result) => {
        if (result.isConfirmed) {
            productosEnCarrito.length = 0;
            localStorage.setItem("productos-en-carrito", JSON.stringify(productosEnCarrito));
            cargarProductosCarrito();
        }
      })
}


function actualizarTotal() {
    const totalCalculado = productosEnCarrito.reduce((acc, producto) => acc + (producto.precio * producto.cantidad), 0);
    total.innerText = `$${totalCalculado}`;
}

botonComprar.addEventListener("click", comprarCarrito);
function comprarCarrito() {

    productosEnCarrito.length = 0;
    localStorage.setItem("productos-en-carrito", JSON.stringify(productosEnCarrito));
    
    contenedorCarritoVacio.classList.add("disabled");
    contenedorCarritoProductos.classList.add("disabled");
    contenedorCarritoAcciones.classList.add("disabled");
    contenedorCarritoComprado.classList.remove("disabled");

}