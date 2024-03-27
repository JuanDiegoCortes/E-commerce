/*const productoController = {
    mostrarProductos: function() {
      fetch('http://localhost:8080/Apiweb/v1/producto/')
        .then(response => response.json())
        .then(datos => {
          const contenedorProductos = document.querySelector('#contenedor-productos');
          contenedorProductos.innerHTML = ''; // Limpiar el contenedor antes de agregar nuevos productos
          datos.forEach(producto => {
            const productoElement = document.createElement('div');
            productoElement.classList.add('producto');
            productoElement.innerHTML = `
              <img src="${producto.image_Url}" alt="${producto.nombre}">
              <div class="producto-detalles">
                <h3 class="producto-titulo">${producto.nombre}</h3>
                <p>${producto.descripcion}</p>
                <p class="producto-precio">Precio: ${producto.precio}</p>
                <button class="producto-agregar" id="${producto.id}">Agregar</button>
              </div>
            `;
            contenedorProductos.appendChild(productoElement);
          });
        })
        .catch(error => console.error('Error:', error));
    },

    filtrarPorGenero: function(genero) {
      fetch(`http://localhost:8080/Apiweb/v1/producto/${genero}`, {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json'
        }
      })
      .then(response => {
        if (!response.ok) {
          throw new Error('Error al filtrar productos por género');
        }
        return response.json();
      })
      .then(productos => {
        console.log('Productos filtrados por género:', productos);
        // Mostrar los productos filtrados
        this.mostrarProductos();
      })
      .catch(error => {
        console.error('Error al filtrar productos por género:', error);
      });
    },

    filtrarPorCategoria: function(categoria) {
      fetch(`http://localhost:8080/Apiweb/v1/producto/${categoria}`, {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json'
        }
      })
      .then(response => {
        if (!response.ok) {
          throw new Error('Error al filtrar productos por categoria');
        }
        return response.json();
      })
      .then(productos => {
        console.log('Productos filtrados por categoria:', productos);
        // Mostrar los productos filtrados
        this.mostrarProductos();
      })
      .catch(error => {
        console.error('Error al filtrar productos por género:', error);
      });
    },
  
    crearProducto: function(productoDTO) {
      fetch('http://localhost:8080/Apiweb/v1/producto/', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(productoDTO)
      })
      .then(response => {
        if (!response.ok) {
          throw new Error('Error al crear el producto');
        }
        return response.json();
      })
      .then(data => {
        console.log('Producto creado con éxito:', data);
      })
      .catch(error => {
        console.error('Error al crear el producto:', error);
      });
    },
  
    actualizarProductoPorId: function(productoId, detallesProducto) {
      fetch(`http://localhost:8080/Apiweb/v1/producto/${productoId}`, {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(detallesProducto)
      })
      .then(response => {
        if (!response.ok) {
          throw new Error('Error al actualizar el producto');
        }
        return response.json();
      })
      .then(data => {
        console.log('Producto actualizado con éxito:', data);
      })
      .catch(error => {
        console.error('Error al actualizar el producto:', error);
      });
    },
  
    obtenerProductoPorId: function(productoId) {
      fetch(`http://localhost:8080/Apiweb/v1/producto/${productoId}`)
      .then(response => {
        if (!response.ok) {
          throw new Error('Error al obtener el producto');
        }
        return response.json();
      })
      .then(producto => {
        console.log('Producto:', producto);
      })
      .catch(error => {
        console.error('Error al obtener el producto:', error);
      });
    }
};
  
export default productoController;
*/