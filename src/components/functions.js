//Metodo para obtener los productos de la base de datos
fetch('http://localhost:8080/Apiweb/v1/producto/')
  .then(response => response.json())
  .then(datos => {
    const productGrid = document.querySelector('.product-grid');
    datos.forEach(producto => {
      const productoElement = document.createElement('div');
      productoElement.classList.add('producto');
      productoElement.innerHTML = `
        <img src="${producto.image_Url}" alt="${producto.nombre}">
        <h3>${producto.nombre}</h3>
        <p>${producto.descripcion}</p>
        <p>Precio: ${producto.precio}</p>
      `;
      productGrid.appendChild(productoElement);
    });
  })
  .catch(error => console.error('Error:', error));

//Metodo para crear un producto para la base de datos
function crearProducto(productoDTO) {
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
}
  
//Metodo para actualizar el producto de la base de datos
function actualizarProductoPorId(productoId, detallesProducto) {
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
}

//Metodo para obtener un producto por el id del producto
function obtenerProductoPorId(productoId) {
  fetch(`http://localhost:8080/Apiweb/v1/producto/${productoId}`)
  .then(response => {
    if (!response.ok) {
      throw new Error('No se pudo obtener el producto');
    }
    return response.json();
  })
  .then(producto => {
    console.log('Producto obtenido:', producto);
  })
  .catch(error => {
    console.error('Error al obtener el producto:', error);
  });
}

//Metodo para filtrar los productos por genero
function filtrarProductosPorGenero(genero) {
  fetch(`http://localhost:8080/Apiweb/v1/producto/${genero}`)
  .then(response => {
    if (!response.ok) {
      throw new Error('No se pudo filtrar por género');
    }
    return response.json();
  })
  .then(productos => {
    console.log(`Productos filtrados por género ${genero}:`, productos);
  })
  .catch(error => {
    console.error('Error al filtrar productos por género:', error);
  });
}
