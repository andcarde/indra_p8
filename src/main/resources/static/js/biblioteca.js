// ==== Helpers de modales ====
function abrirModal(selector) {
    $(selector).css("display", "flex");
}

function cerrarModal(selector) {
    $(selector).css("display", "none");
}

// ==== Inicio ====
$(document).ready(function () {
    cargarLibros();

    // Botones de la toolbar
    $("#btnPrestar").click(() => abrirModal("#modalPrestar"));
    $("#btnMostrarPrestamos").click(mostrarPrestamos);
    $("#btnDevolverPrestamo").click(() => abrirModal("#modalDevolver"));
    $("#btnCrearAutor").click(() => abrirModal("#modalAutor"));
    $("#btnCrearLibro").click(() => abrirModal("#modalLibro"));
    $("#btnGestionarSocios").click(() => {
        abrirModal("#modalSocios");
        mostrarSeccionSocios("listar");
        cargarSocios();
    });
    $(".btn-socios").click(function () {
        const section = $(this).data("section");
        mostrarSeccionSocios(section);

        if (section === "listar") {
            cargarSocios();
        }
    });
    $("#btnCargarSocio").click(() => {
        const id = $("#lectorIdEditar").val();
        if (!id) {
            showMessage("Introduce el ID de socio para editar.");
            return;
        }
        cargarSocioEnFormulario(id);
    });
    $("#btnGuardarSocio").click(() => {
        guardarSocio();
    });
    $("#btnEliminarSocio").click(() => {
        const id = $("#lectorIdEliminar").val();
        if (!id) {
            showMessage("Introduce el ID de socio a eliminar.");
            return;
        }
        eliminarSocio(id);
    });
});

// ==== Carga y renderizado de libros ====
function cargarLibros() {
    $.get("/biblioteca/libros", function (lista) {
        const $lista = $("#listaLibros").empty();

        if (!lista || lista.length === 0) {
            $lista.append("<em>No hay libros creados.</em>");
            return;
        }

        lista.forEach(libro => {
            const card = $(`
                <div class="libro-card">
                    ${libro.titulo || "Sin título"}
                </div>
            `);

            card.click(() => {
                irADetalleLibro(libro.id);
            });

            $lista.append(card);
        });

        $("#output").text("Cargados " + lista.length + " libros.");
    }).fail(err => {
        $("#output").text("Error al cargar libros: " + (err.status || "") + " " + (err.statusText || ""));
    });
}

// Navegación al detalle de libro (libro.html)
function irADetalleLibro(idLibro) {
    if (!idLibro)
        return;
    window.location.href = "/libro?id=" + encodeURIComponent(idLibro);
}


// ==== Mostrar préstamos de un lector ====
function mostrarPrestamos() {
    const idLector = prompt("ID del lector del que quiere ver los préstamos:");
    if (!idLector) return;

    $.get("/biblioteca/prestamosLector/" + encodeURIComponent(idLector), function (data) {
        $("#output").text(JSON.stringify(data, null, 2));
    }).fail(err => {
        $("#output").text("Error al obtener préstamos: " + (err.status || "") + " " + (err.statusText || ""));
    });
}

// ==== Devolver préstamo (desde modal simple) ====
function devolverPrestamoDesdeModal() {
    const idPrestamo = $("#inpIdPrestamo").val();
    if (!idPrestamo) return;

    $.ajax({
        url: "/biblioteca/devolucion" + encodeURIComponent(idPrestamo),
        type: "PUT",
        success: r => {
            $("#output").text("Resultado devolución: " + r);
            cerrarModal("#modalDevolver");
        },
        error: e => {
            $("#output").text("Error al devolver: " + (e.status || "") + " " + (e.statusText || ""));
        }
    });
}

// ==== Crear autor ====
function crearAutor() {
    const payload = {
        nombre: $("#autorNombre").val(),
        nacionalidad: $("#autorNacionalidad").val(),
        fechaNac: $("#fechaNac").val()
    };

    $.ajax({
        url: "/biblioteca/crearAutor",
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify(payload),
        success: () => {
            $("#output").text("Autor creado correctamente.");
            cerrarModal("#modalAutor");
        },
        error: e => {
            $("#output").text("Error al crear autor: " + (e.status || "") + " " + (e.statusText || ""));
        }
    });
}

$(document).ready(() => {
    $("#btnConfirmarPrestamo").on("click", function () {
        let idCopia = $("#prestar_idCopia").val();
        let idLector = $("#prestar_idLector").val();
        let body = {
            idCopia: idCopia,
            idLector: idLector
        }

        $.ajax({
            url: `/biblioteca/prestar`,
            type: "POST",
            data: JSON.stringify(body),
            contentType: "application/json",
            success: function (message) {
                cerrarModal("#modalPrestar");
                if (!message)
                    showMessage("Se ha prestado la copia.");
                else
                    showMessage(message);
            },
            error: function (jqXHR, textStatus, errorThrown) {
                cerrarModal("#modalPrestar");
                showMessage("No se ha podido prestar la copia.");
            }
        });
    });
});

// ==== Crear libro ====
function crearLibro() {
    const titulo= $("#libroTituloInput").val().trim();
    if(!titulo){
        showMessage("Falta el titulo del libro");
        return;
    }
    const payload = {
        titulo: $("#libroTituloInput").val(),
        tipo: $("#libroTipo").val(),
        editorial: $("#libroEditorial").val(),
        isbn:$("#libroISBN").val(),
        anyo: parseInt($("#libroAnyo").val(), 10),
        idAutor: parseInt($("#libroAutorId").val(), 10)
    };

    $.ajax({
        url: "/biblioteca/crearlibro"+encodeURIComponent(idPrestamo)+"/delete",
        type: "Delete",
        contentType: "application/json",
        data: JSON.stringify(payload),
        success: () => {
            $("#output").text("Libro creado correctamente.");
        },
        error: e => {
            $("#output").text("Error al crear libro: " + (e.status || "") + " " + (e.statusText || ""));
        }
    });
}

function mostrarSeccionSocios(section) {
    $(".sec-socios").hide();

    if (section === "listar") {
        $("#secListarSocios").show();
    } else if (section === "form") {
        $("#secFormSocio").show();
    } else if (section === "eliminar") {
        $("#secEliminarSocio").show();
    }
}

function cargarSocios() {
    $.get("/biblioteca/lectores", function (lista) {
        const $tbody = $("#tbodySocios").empty();

        if (!lista || lista.length === 0) {
            $tbody.append(`
                <tr>
                    <td colspan="7"><em>No hay socios registrados.</em></td>
                </tr>
            `);
            return;
        }

        lista.forEach(lector => {
            const fila = $(`
                <tr>
                    <td>${lector.id}</td>
                    <td>${lector.nombre || ""}</td>
                    <td>${lector.apellido || ""}</td>
                    <td>${lector.telefono || ""}</td>
                    <td>${lector.email || ""}</td>
                    <td>${lector.direccion || ""}</td>
                    <td>${lector.estado || ""}</td>
                </tr>
            `);
            $tbody.append(fila);
        });
    }).fail(err => {
        showMessage("Error al cargar socios: " + (err.status || "") + " " + (err.statusText || ""));
    });
}

function cargarSocioEnFormulario(id) {
    $.get("/biblioteca/lectores/" + encodeURIComponent(id), function (lector) {
        $("#lectorNombre").val(lector.nombre || "");
        $("#lectorApellido").val(lector.apellido || "");
        $("#lectorTelefono").val(lector.telefono || "");
        $("#lectorEmail").val(lector.email || "");
        $("#lectorDireccion").val(lector.direccion || "");
        $("#lectorEstado").val(lector.estado || "ACTIVO");
    }).fail(err => {
        showMessage("No se ha podido cargar el socio: " + (err.status || "") + " " + (err.statusText || ""));
    });
}

function recogerDatosFormularioSocio() {
    return {
        nombre: $("#lectorNombre").val(),
        apellido: $("#lectorApellido").val(),
        telefono: $("#lectorTelefono").val(),
        email: $("#lectorEmail").val(),
        direccion: $("#lectorDireccion").val(),
        estado: $("#lectorEstado").val()
        // idPrestamos e idMulta los dejamos vacíos para este CRUD básico
    };
}

function limpiarFormularioSocio() {
    $("#lectorIdEditar").val("");
    $("#lectorNombre").val("");
    $("#lectorApellido").val("");
    $("#lectorTelefono").val("");
    $("#lectorEmail").val("");
    $("#lectorDireccion").val("");
    $("#lectorEstado").val("ACTIVO");
}

function guardarSocio() {
    const id = $("#lectorIdEditar").val();
    const payload = recogerDatosFormularioSocio();

    if (!payload.nombre || !payload.apellido) {
        showMessage("Nombre y apellido son obligatorios.");
        return;
    }

    // Si hay ID -> actualizar
    if (id) {
        $.ajax({
            url: "/biblioteca/lectores/" + encodeURIComponent(id),
            type: "PUT",
            contentType: "application/json",
            data: JSON.stringify(payload),
            success: () => {
                showMessage("Socio actualizado correctamente.");
                cargarSocios();
            },
            error: e => {
                showMessage("Error al actualizar socio: " + (e.status || "") + " " + (e.statusText || ""));
            }
        });
    } else {
        // Sin ID -> crear
        $.ajax({
            url: "/biblioteca/lectores",
            type: "POST",
            contentType: "application/json",
            data: JSON.stringify(payload),
            success: () => {
                showMessage("Socio creado correctamente.");
                limpiarFormularioSocio();
                cargarSocios();
            },
            error: e => {
                showMessage("Error al crear socio: " + (e.status || "") + " " + (e.statusText || ""));
            }
        });
    }
}

function eliminarSocio(id) {
    $.ajax({
        url: "/biblioteca/lectores/" + encodeURIComponent(id),
        type: "DELETE",
        success: () => {
            showMessage("Socio eliminado correctamente.");
            $("#lectorIdEliminar").val("");
            cargarSocios();
        },
        error: e => {
            showMessage("Error al eliminar socio: " + (e.status || "") + " " + (e.statusText || ""));
        }
    });
}



