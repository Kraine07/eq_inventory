const userForm = document.getElementById("user-form")
const role = document.getElementById("role")
const toggleSwitch = document.getElementById("toggleSwitch")

// Role values
// const adminValue = document.getElementById("admin").innerText
// const editorValue = document.getElementById("editor").innerText




// click outside to close user form
window.onclick = function (event) {
    if (event.target == userForm) {
        userForm.style.display = "none"
        location.reload()
    }
}


// change role value
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