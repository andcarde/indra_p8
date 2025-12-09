if (!MESSAGE_JS)
    alert("Fallo de dependencias en prestar.js")

$(document).ready(() => {
    $("#prestar_fin").on("change", function () {
        let valor = $(this).val();
        if (!valor)
            return;

        let limite = new Date(valor);
        let hoy = new Date();
        let manana = new Date();
        let maximo = new Date();

        // Normalizar
        hoy.setHours(0,0,0,0);
        limite.setHours(0,0,0,0);

        // Definir límites
        manana.setDate(hoy.getDate() + 1);
        maximo.setDate(hoy.getDate() + 30);

        // Formatear
        const formatear = (f) =>
            f.toISOString().split("T")[0];

        // · VALIDACIONES

        // Fecha anterior a hoy
        if (limite < hoy) {
            showMessage("La fecha no puede ser anterior a hoy.");
            $(this).val(formatear(manana));
        }
        // Fecha igual a hoy
        else if (limite.getTime() === hoy.getTime()) {
            showMessage("La fecha no puede ser hoy. Debe ser al menos mañana.");
            $(this).val(formatear(manana));
        }
        // Fecha superior al límite
        else if (limite > maximo) {
            showMessage("La fecha no puede despues de dentro de 30 días.");
            $(this).val(formatear(maximo));
        }
    });

    $("#btnConfirmarPrestamo").on("click", function () {
        let idCopia = $("#prestar_idCopia").val();
        let idLector = $("#prestar_idLector").val();
        let fechaLimite = $("#prestar_fechaLimite").val();

        let body = {
            idCopia,
            idLector,
            fechaLimite
        }

        $.ajax({
            url: `/biblioteca/prestar`,
            type: "POST",
            data: JSON.stringify(body),
            contentType: "application/json",
            success: function () {
                cerrarModal("#modalPrestar");
                showMessage("Se ha prestado la copia.");
            },
            error: function (jqXHR, textStatus, errorThrown) {
                closeAndShowMessage(jqXHR,
                    "Prestar",
                    "No se ha podido prestar la copia.");
            }
        });
    });
});