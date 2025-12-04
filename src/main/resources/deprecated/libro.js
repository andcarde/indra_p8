
function mostrarPaginaLibro(idLibro) {
    $.ajax({
        url: '/biblioteca/getLibro' + idLibro,
        type: 'GET',
        success: function(libro) {
            if (!libro) {
                $('body').html('<div class="container mt-5"><div class="alert alert-danger">Libro no encontrado.</div></div>');
                return;
            }

            let numCopias = libro.copias ? libro.copias.length : 0;

            let html = `
                <div class="container mt-4">
                    <h1 class="text-primary mb-4">${libro.titulo}</h1>

                    <div class="card mb-4 shadow-sm">
                        <div class="card-body">
                            <h5 class="card-title">Ficha del Libro</h5>
                            <p><strong>Autor:</strong> ${libro.nombreAutor}</p>
                            <p><strong>Número de copias:</strong> ${numCopias}</p>
                        </div>
                    </div>

                    <div class="card shadow-sm">
                        <div class="card-body">
                            <h5 class="card-title">Copias</h5>
                            <div class="table-responsive">
                                <table class="table table-striped table-hover">
                                    <thead class="table-primary">
                                        <tr>
                                            <th>Id</th>
                                            <th>Estado</th>
                                            <th>Acción</th>
                                        </tr>
                                    </thead>
                                    <tbody>
            `;

            (libro.copias || []).forEach(copia => {
                let acciones = '';

                switch (copia.estado) {
                    case 'prestado':
                        acciones = `
                            <button class="btn btn-sm btn-success me-1">Devolver a biblioteca</button>
                        `;
                        break;
                    case 'retraso':
                        acciones = `
                            <button class="btn btn-sm btn-info me-1">Ver prestatario</button>
                        `;
                        break;
                    case 'biblioteca':
                        acciones = `
                            <button class="btn btn-sm btn-danger me-1">Dar de baja</button>
                            <button class="btn btn-sm btn-warning">Enviar a reparación</button>
                        `;
                        break;
                    case 'reparacion':
                        acciones = `<span class="text-muted">En reparación</span>`;
                        break;
                    default:
                        acciones = '';
                }

                html += `
                    <tr>
                        <td>${copia.id}</td>
                        <td>${copia.estado}</td>
                        <td>${acciones}</td>
                    </tr>
                `;
            });

            html += `
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            `;

            $('body').html(html);
        },
        error: function() {
            $('body').html('<div class="container mt-5"><div class="alert alert-danger">Error al cargar el libro.</div></div>');
        }
    });
}

