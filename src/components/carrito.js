let productosEnCarrito = localStorage.getItem("productos-en-carrito");
productosEnCarrito = JSON.parse(productosEnCarrito);

const contenedorCarritoVacio = document.querySelector("#carrito-vacio");
const contenedorCarritoProductos = document.querySelector("#carrito-productos");
const contenedorCarritoAcciones = document.querySelector("#carrito-acciones");
const botonVaciar = document.querySelector("#carrito-acciones-vaciar");
const contenedorTotal = document.querySelector("#total");
const botonComprar = document.querySelector("#carrito-acciones-comprar");

async function cargarProductosCarrito() {
    if (productosEnCarrito && productosEnCarrito.length > 0) {
        contenedorCarritoVacio.classList.add("disabled");
        contenedorCarritoProductos.classList.remove("disabled");
        contenedorCarritoAcciones.classList.remove("disabled");

        contenedorCarritoProductos.innerHTML = "";

        for (const producto of productosEnCarrito) {
            const data = await buscarTallasProducto(producto.idProducto);

            const divProducto = document.createElement("div");
            divProducto.classList.add("carrito-producto");
            divProducto.innerHTML = `
                <img class="carrito-producto-imagen" src="${producto.image_Url}" alt="${producto.nombre}">
                <div class="carrito-producto-titulo">
                    <small>Título</small>
                    <h3>${producto.nombre}</h3>
                </div>
                <div class="carrito-producto-cantidad">
                    <small>Cantidad</small>
                    <input type="number" data-id="${producto.idProducto}" class="carrito-producto-input-cantidad" value="${producto.cantidad}" min="1">
                </div>
                <div class="dropdown">
                    <button id="boton-tallas" class="btn btn-primary dropdown-toggle boton-tallas" type="button" data-bs-toggle="dropdown" aria-expanded="false">
                        Tallas
                    </button>
                    <ul class="dropdown-menu">
                        <li><button id="1" class="dropdown-item boton-talla1" type="button">XS</button></li>
                        <li><button id="2" class="dropdown-item boton-talla2" type="button">S</button></li>
                        <li><button id="3" class="dropdown-item boton-talla3" type="button">M</button></li>
                        <li><button id="4" class="dropdown-item boton-talla4" type="button">L</button></li>
                        <li><button id="5" class="dropdown-item boton-talla5" type="button">XL</button></li>
                        <li><button id="6" class="dropdown-item boton-talla6" type="button">UNICA</button></li>
                    </ul>
                </div>
                <div class="carrito-producto-precio">
                    <small>Precio</small>
                    <p>$${producto.precio}</p>
                </div>
                <div class="carrito-producto-subtotal">
                    <small>Subtotal</small>
                    <p>$${producto.precio * producto.cantidad}</p>
                </div>
                <div class="personalizacion">
                    <button class="carrito-producto-personalizar" id="${producto.idProducto}"><i class="bi bi-pencil-fill"></i></button>
                </div>
                <button class="carrito-producto-eliminar" id="${producto.idProducto}"><i class="bi bi-trash-fill"></i></button>
            `;

            if (producto.personalizable !== "si") {
                const botonPersonalizar = divProducto.querySelector('.carrito-producto-personalizar');
                botonPersonalizar.style.display = "none"; 
            }

            data.map(item => {
                let idCategoria = item.idProducto.idCategoria.idCategoria;
                if (idCategoria === 11 || idCategoria === 12 || idCategoria === 13 || idCategoria === 14) {
                    divProducto.querySelector(`.boton-talla6`).style.display = "none";
                } else if (idCategoria === 15){
                    divProducto.querySelector(`.boton-talla1`).style.display = "none";
                    divProducto.querySelector(`.boton-talla2`).style.display = "none";
                    divProducto.querySelector(`.boton-talla3`).style.display = "none";
                    divProducto.querySelector(`.boton-talla4`).style.display = "none";
                    divProducto.querySelector(`.boton-talla5`).style.display = "none";
                }
            });

            contenedorCarritoProductos.appendChild(divProducto);
        }

        actualizarBotonesEliminar();
        actualizarTotal();
        actualizarBotonesTallas();
        actualizarCantidadInput();

    } else {
        contenedorCarritoVacio.classList.remove("disabled");
        contenedorCarritoProductos.classList.add("disabled");
        contenedorCarritoAcciones.classList.add("disabled");
    }
}


cargarProductosCarrito();

function actualizarCantidadInput() {
    let inputsCantidad = document.querySelectorAll('.carrito-producto-input-cantidad');

    inputsCantidad.forEach(input => {
        input.addEventListener('change', (e) => {
            let nuevaCantidad = parseInt(e.target.value);
            let idProducto = parseInt(e.target.dataset.id); 

            let i = productosEnCarrito.findIndex(producto => producto.idProducto === idProducto);
            if (i !== -1) {
                productosEnCarrito[i].cantidad = nuevaCantidad;
            }

            localStorage.setItem('productos-en-carrito', JSON.stringify(productosEnCarrito));

            actualizarTotal();
            cargarProductosCarrito();
        });
    });
}

function actualizarBotonesEliminar() {
    const botonesEliminar = document.querySelectorAll(".carrito-producto-eliminar");
    botonesEliminar.forEach(boton => {
        boton.addEventListener("click", eliminarDelCarrito);
    });
}

function actualizarBotonesTallas() {
    const botonesTallas = document.querySelectorAll(".dropdown-item");
    botonesTallas.forEach(boton => {
        boton.addEventListener("click", function(e) {
            const idTalla = e.currentTarget.id;
            const idProducto = e.currentTarget.closest('.carrito-producto').querySelector('.carrito-producto-eliminar').id;
            const producto = productosEnCarrito.find(producto => producto.idProducto === parseInt(idProducto));
            producto.idTalla = parseInt(idTalla);
            localStorage.setItem("productos-en-carrito", JSON.stringify(productosEnCarrito));
        });
    });
}

async function buscarTallasProducto(idProducto) {
    const url = `http://localhost:8081/Apiweb/v1/prodTalla/prodTallaPorIdProducto/${idProducto}`;
    const response = await fetch(url);
    const data = await response.json();
    return data;
}

function eliminarDelCarrito(e) {
    Toastify({
        text: "Producto eliminado",
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
    const index = productosEnCarrito.findIndex(producto => producto.idProducto === parseInt(idBoton));
      
    productosEnCarrito.splice(index, 1);
    cargarProductosCarrito();

    localStorage.setItem("productos-en-carrito", JSON.stringify(productosEnCarrito));
}

botonVaciar.addEventListener("click", () => {
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
});

function actualizarTotal() {
    const totalCalculado = productosEnCarrito.reduce((acc, producto) => acc + (producto.precio * producto.cantidad), 0);
    contenedorTotal.innerText = `$${totalCalculado}`;
}

botonComprar.addEventListener("click", () =>  {
    const infoUsuario = localStorage.getItem("usuario");
    let usuario = JSON.parse(infoUsuario);
    cedulaUsuario = usuario.cedula.cedula
    if (usuario === null) {
        Toastify({
            text: "Por favor inicie sesión para comprar",
            duration: 3000,
            close: true,
            gravity: "top", 
            position: 'right',
            style: {
                background: "linear-gradient(to right, #4b33a8, #785ce9)",
                borderRadius: "2rem",
                textTransform: "uppercase",
                fontSize: ".75rem"
            },
          }).showToast();
          
          setTimeout(function(){
            window.location.href = "../pages/login.html";
          }, 3100);
    } else {
        if (productosEnCarrito.some(producto => producto.idTalla === undefined || producto.idTalla === 0)) {
            Toastify({
                text: "Por favor seleccione una talla para todos los productos.",
                duration: 3000,
                close: true,
                gravity: "top", 
                position: 'right',
                style: {
                    background: "linear-gradient(to right, #4b33a8, #785ce9)",
                    borderRadius: "2rem",
                    textTransform: "uppercase",
                    fontSize: ".75rem"
                },
            }).showToast();
        } else {
            const orden = {
                estado: "realizacion",
                metodoPago: "",
                precioTotal: productosEnCarrito.reduce((acc, producto) => acc + (producto.precio * producto.cantidad), 0),
                idEnvio: { idEnvio: 0 },
                cedula: { cedula: cedulaUsuario },
                Productos: productosEnCarrito.map(producto => ({
                    cantidad: producto.cantidad,
                    image_Personalizacion: producto.image_Personalizacion || "",
                    texto_Personalizaion: producto.texto_Personalizaion || "",
                    idProducto: producto.idProducto,
                    idTalla: producto.idTalla
                }))
            }
            localStorage.setItem("orden", JSON.stringify(orden));
            window.location.href = "../pages/envio.html";
            contenedorCarritoVacio.classList.add("disabled");
            contenedorCarritoProductos.classList.add("disabled");
            contenedorCarritoAcciones.classList.add("disabled");
        };
    }
});
