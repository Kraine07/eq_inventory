const userForm = document.getElementById("user-form")
const role = document.getElementById("role")
const toggleSwitch = document.getElementById("toggleSwitch")



// click outside to close user form
window.onclick = function (event) {
    if (event.target == userForm) {
        userForm.style.display = "none"
        location.reload()
    }
}


// change role value
if (toggleSwitch) {
    toggleSwitch.addEventListener('change', () => {
        if (toggleSwitch.checked) {
            role.checked = false
            role.setAttribute('name', '')
            toggleSwitch.setAttribute('name','role')

        } else {
            role.checked =true
            role.setAttribute('name', 'role')
            toggleSwitch.setAttribute('name','')
        }

    })
}



// when page loads
window.onload = function () {
    const autoForm = document.getElementById('auto-form');
    if (autoForm) {
        autoForm.submit();
    }
};
