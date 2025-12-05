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

            if (copia.estado === "prestado") {
                $("#btnBaja").hide();
                $("#btnReparar").hide();
                $("#btnDevolver").show();
            } else {
                $("#btnBaja").show();
                $("#btnReparar").show();
                $("#btnDevolver").hide();
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
            alert("Copia " + idCopia + " dada de baja correctamente.");
            // window.location.href = "/biblioteca"; // o volver al libro
            const idLibro = $("#idCopia").data("libro-id");
            window.location.href = "/libro?id=" + encodeURIComponent(idLibro);
        },
        error: function () {
            alert("Error al dar de baja la copia " + idCopia);
        }
    });
}

function enviarReparacionCopia(idCopia) {
    $.ajax({
        url: '/biblioteca/copia/' + encodeURIComponent(idCopia) + '/reparar',
        type: 'PUT',
        success: function () {
            alert("Copia " + idCopia + " enviada a reparación.");
        },
        error: function () {
            alert("Error al enviar a reparación la copia " + idCopia);
        }
    });
}

function devolvere(idCopia) {
    $.ajax({
        url: '/devolver/{idCopia}' + encodeURIComponent(idCopia),
        type: 'POST',
        success: function () {
            alert("Copia " + idCopia + " devuelta correctamente.");
            // window.location.href = "/biblioteca"; // o volver al libro
            const idLibro = $("#idCopia").data("libro-id");
            window.location.href = "/libro?id=" + encodeURIComponent(idLibro);
        },
        error: function () {
            alert("Error al devolver la copia " + idCopia);
        }
    });
}
