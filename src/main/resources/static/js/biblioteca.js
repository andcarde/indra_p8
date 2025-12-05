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
    const payload = {
        titulo: $("#libroTituloInput").val(),
        tipo: $("#libroTipo").val(),
        editorial: $("#libroEditorial").val(),
        anyo: parseInt($("#libroAnyo").val(), 10),
        idAutor: parseInt($("#libroAutorId").val(), 10)
    };

    $.ajax({
        url: "/biblioteca/crearlibro",
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify(payload),
        success: () => {
            $("#output").text("Libro creado correctamente.");
            cerrarModal("#modalLibro");
            cargarLibros(); // recarga la lista
        },
        error: e => {
            $("#output").text("Error al crear libro: " + (e.status || "") + " " + (e.statusText || ""));
        }
    });
}
