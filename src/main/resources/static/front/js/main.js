document.addEventListener("DOMContentLoaded", () => {
    const notifierToast = document.getElementById("notifierToast");

    if (typeof notifierToast === 'undefined' || notifierToast === null) {
        return;
    }
    const toast = new bootstrap.Toast(notifierToast);
    toast.show();
});

const handleToggle = (action) => {
    console.log(action)
    const form = document.createElement("form");
    form.setAttribute("action", action);
    form.setAttribute("method", "POST");
    document.body.append(form);
    form.submit();
}