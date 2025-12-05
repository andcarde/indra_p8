$(document).ready(function () {
    const params = new URLSearchParams(window.location.search);
    const idCopia = params.get("id");

    if (!idCopia) {
        $("body").html(
            '<div class="container mt-5"><div class="alert alert-warning">No se ha indicado ninguna copia.</div></div>'
        );
        return;
    }

    cargarCopia(idCopia);

    $("#btnBaja").click(() => darDeBajaCopia(idCopia));
    $("#btnReparar").click(() => enviarReparacionCopia(idCopia));
    $("#btnBiblioteca").click(() => enviarBibliotecaCopia(idCopia));
    $("#btnDevolver").click(()=>devolvere(idCopia));
});

function cargarCopia(idCopia) {
    $.ajax({
        url: '/biblioteca/getCopia' + encodeURIComponent(idCopia),  // @GetMapping("/getCopia/{idCopia}")
        type: 'GET',
        success: function (copia) {
            if (!copia) {
                $("body").html(
                    '<div class="container mt-5"><div class="alert alert-danger">Copia no encontrada.</div></div>'
                );
                return;
            }

            $("#idCopia").text(copia.id);
            $("#idCopia").data("libro-id", copia.libro.id);
            $("#estadoCopia").text(copia.estado || "—");

            // Valores default
            $("#btnBaja").css("display", "none");
            $("#btnReparar").css("display", "none");
            $("#btnDevolver").css("display", "none");
            $("#btnBiblioteca").css("display", "none");

            if (copia.estado === "PRESTADO")
                $("#btnDevolver").css("display", "flex");
            else if (copia.estado === "REPARACION")
                $("#btnBiblioteca").css("display", "flex");
            else if (copia.estado === "BIBLIOTECA") {
                $("#btnBaja").css("display", "flex");
                $("#btnReparar").css("display", "flex");
            }
        },
        error: function () {
            $("body").html(
                '<div class="container mt-5"><div class="alert alert-danger">Error al cargar la copia.</div></div>'
            );
        }
    });
}

function darDeBajaCopia(idCopia) {
    $.ajax({
        url: '/biblioteca/copia/' + encodeURIComponent(idCopia) + '/baja',
        type: 'DELETE',
        success: function () {
            showMessage("Copia " + idCopia + " dada de baja correctamente.");
            // window.location.href = "/biblioteca"; // o volver al libro
            const idLibro = $("#idCopia").data("libro-id");
            window.location.href = "/libro?id=" + encodeURIComponent(idLibro);
        },
        error: function () {
            showMessage("Error al dar de baja la copia " + idCopia);
        }
    });
}

function enviarReparacionCopia(idCopia) {
    $.ajax({
        url: '/biblioteca/copia/' + encodeURIComponent(idCopia) + '/reparar',
        type: 'PUT',
        success: function () {
            showMessage("Copia " + idCopia + " enviada a reparación.");
            window.location.href = "/copia?id=" + encodeURIComponent(idCopia);
        },
        error: function () {
            showMessage("Error al enviar a reparación la copia " + idCopia);
        }
    });
}

function volver() {
    const idLibro = $("#idCopia").data("libro-id");
    window.location.href = "/libro?id=" + encodeURIComponent(idLibro);
}

function devolvere(idCopia) {
    $.ajax({
        url: 'biblioteca/devolver/' + encodeURIComponent(idCopia),
        type: 'PUT',
        success: function () {
            showMessage("Copia " + idCopia + " devuelta correctamente.")
            // window.location.href = "/biblioteca"; // o volver al libro
            const idLibro = $("#idCopia").data("libro-id");
            window.location.href = "/libro?id=" + encodeURIComponent(idLibro);
        },
        error: function () {
            showMessage("Error al devolver la copia " + idCopia);
        }
    });
}


function enviarBibliotecaCopia(idCopia) {
    $.ajax({
        url: '/biblioteca/copia/' + encodeURIComponent(idCopia) + '/biblioteca',
        type: 'PUT',
        success: function () {
            showMessage("Copia " + idCopia + " enviada a biblioteca.");
            window.location.href = "/copia?id=" + encodeURIComponent(idCopia);
        },
        error: function () {
            showMessage("Error al enviar a biblioteca la copia " + idCopia);
        }
    });
}