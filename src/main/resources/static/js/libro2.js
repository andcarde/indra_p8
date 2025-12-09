$(document).ready(function () {
    const params = new URLSearchParams(window.location.search);
    const idLibro = params.get("id");

    if (!idLibro) {
        $("body").html(
            '<div class="container mt-5"><div class="alert alert-warning">No se ha indicado ningún libro.</div></div>'
        );
        return;
    }

    cargarLibro(idLibro);
    cargarCopiasLibro(idLibro);
    $("#btnCrearCopia").click(() => abrirModal("#modalNumCopias"));
    $("#btnModificarLibro").click(() => abrirModal("#modalUpdateLibro"));
});

function cargarLibro(idLibro) {
    $.ajax({
        url: '/biblioteca/getLibro' + encodeURIComponent(idLibro),
        type: 'GET',
        success: function (libro) {
            if (!libro) {
                $("body").html(
                    '<div class="container mt-5"><div class="alert alert-danger">Libro no encontrado.</div></div>'
                );
                return;
            }

            $("#tituloLibro").text(libro.titulo || "Libro");
            $("#nombreAutor").text(libro.autor.nombre + " es de " + libro.autor.nacionalidad|| "");
            $("#ISBN").text(libro.isbn);
            const numCopias = libro.copias ? libro.copias.length : "—";
            $("#numCopias").text(numCopias);
        },
        error: function () {
            $("body").html(
                '<div class="container mt-5"><div class="alert alert-danger">Error al cargar el libro.</div></div>'
            );
        }
    });
}

function cargarCopiasLibro(idLibro) {
    $.ajax({
        url: '/biblioteca/getCopias' + encodeURIComponent(idLibro),  // @GetMapping("/getCopias/{idLibro}")
        type: 'GET',
        success: function (copias) {
            const $tbody = $("#tbodyCopias");
            $tbody.empty();

            if (!copias || copias.length === 0) {
                $tbody.append(`
                    <tr>
                        <td colspan="3" class="text-center text-muted">
                            Este libro no tiene copias todavía.
                        </td>
                    </tr>
                `);
                $("#numCopias").text("0");
                return;
            }

            $("#numCopias").text(copias.length);

            copias.forEach(copia => {
                const estado = copia.estado || "—";

                const $fila = $("<tr></tr>");
                $fila.append(`<td>${copia.id}</td>`);
                $fila.append(`<td>${estado}</td>`);

                const $btnDetalle = $(`
                    <button class="btn btn-sm btn-primary">
                        Ver detalle
                    </button>
                `);

                $btnDetalle.click(() => {
                        window.location.href = "/copia?id=" + encodeURIComponent(copia.id);
                });

                const $tdAccion = $("<td></td>").append($btnDetalle);
                $fila.append($tdAccion);
                $tbody.append($fila);
            });
        },
        error: function () {
            const $tbody = $("#tbodyCopias");
            $tbody.empty().append(`
                <tr>
                    <td colspan="3" class="text-center text-danger">
                        Error al cargar las copias.
                    </td>
                </tr>
            `);
        }
    });
}

function crearCopiasLibro() {
    const params = new URLSearchParams(window.location.search);
    const idLibro = params.get("id");
    const ncopias= $("#inputNumCopias").val();
    $.ajax({
        url: '/biblioteca/crearcopia' + encodeURIComponent(idLibro),
        type: 'POST',
        data:{ncopias},
        success: function (libro) {
            window.location.href = "/libro?id=" + encodeURIComponent(idLibro);
        },
        error: function () {
            cerrarModal("#modalNumCopias");
            showMessage("No se ha podido crear copias.");
        }
    });
}
function abrirModal(selector) {
    $(selector).css("display", "flex");
}

function cerrarModal(selector) {
    $(selector).css("display", "none");
}

function volver() {
    window.location.href = "/biblioteca";
}

function deleteLibro(){
    const params = new URLSearchParams(window.location.search);
    const idLibro = params.get("id");
    $.ajax({
        url: "/biblioteca/libro/" +encodeURIComponent(idLibro)+"/delete",
        type: "DELETE",
        contentType: "application/json",
        success: () => {
            window.location.href = "/biblioteca";
        },
        error: e => {
            showMessage("No se ha podido borrar el libro");
        }
    });
}
function updateLibro(){
    const params = new URLSearchParams(window.location.search);
    const idLibro = params.get("id");
    const titulo= $("#libroTituloInput").val().trim();
    if(!titulo){
        showMessage("Falta el titulo del libro");
        return;
    }
    const payload = {
        titulo: $("#libroTituloInput").val(),
        tipo: $("#libroTipo").val(),
        editorial: $("#libroEditorial").val(),
        anyo: parseInt($("#libroAnyo").val(), 10),
        isbn:parseInt($("#libroISBN").val()),
        idAutor: parseInt($("#libroAutorId").val(), 10)
    };
    $.ajax({
        url: "/biblioteca/libro/" +encodeURIComponent(idLibro)+"/update",
        type: "PUT",
        contentType: "application/json",
        data:JSON.stringify(payload),
        success: () => {
            window.location.href = "/libro?id=" + encodeURIComponent(idLibro);
        },
        error: e => {
            showMessage("No se ha podido actualizar el libro");
        }
    });
}