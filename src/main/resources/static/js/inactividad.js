let warningTimer, logoutTimer;

function resetTimers() {
    clearTimeout(warningTimer);
    clearTimeout(logoutTimer);

    warningTimer = setTimeout(() => {
        Swal.fire({
            title: '¿Sigues aquí?',
            text: 'Has estado inactivo durante un momento.',
            icon: 'warning',
            confirmButtonText: 'Sí, sigo aquí',
            timer: 30000,
            timerProgressBar: true,
            allowOutsideClick: false,
            customClass: {
                popup: 'mi-popup',
                confirmButton: 'btn-confirmar'
            },
            buttonsStyling: false
        }).then((result) => {
            if (result.isConfirmed) {
                logoutTimer = setTimeout(() => {
                    Swal.fire({
                        icon: 'info',
                        title: 'Sesión finalizada',
                        text: 'Se cerró la sesión por inactividad.',
                        timer: 3000,
                        showConfirmButton: false,
                        customClass: {
                            popup: 'mi-popup'
                        }
                    }).then(() => {
                        window.location.href = "/logout";
                    });
                }, 30000);
            } else {
                window.location.href = "/logout";
            }
        });
    }, 30000);
}

['mousemove', 'keydown', 'click', 'scroll'].forEach(evt =>
    document.addEventListener(evt, resetTimers)
);

resetTimers();
