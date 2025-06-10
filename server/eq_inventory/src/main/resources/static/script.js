



const userForm = document.getElementById("user-form");
const userFormContainer = document.getElementById("user-form-container");
const role = document.getElementById("role");
const toggleSwitch = document.getElementById("toggleSwitch");
const closeUserForm = document.querySelector("#close-user-form");

const confirmationWindow = document.querySelector("#confirm-window");
const confirmationQuestion = document.querySelector("#confirm-question");
const confirmationHeading = document.querySelector("#confirm-heading");
const closeConfirmationWindow = document.querySelector("#confirm-close");
const deleteConfirmationButton = document.querySelector("#delete-confirm-btn");
const statusConfirmationButton = document.querySelector("#status-confirm-btn");

const changeStatusButton = document.querySelector("#change-status-btn");

// admin panel
const manageUserButton = document.querySelector("#manage-user-btn");
const manageEquipmentButton = document.querySelector("#manage-equipment-btn");
const managePropertyButton = document.querySelector("#manage-property-btn");
const manageUserScreen = document.querySelector("#manage-user-screen");
const manageEquipmentScreen = document.querySelector("#manage-equipment-screen");
const managePropertyScreen = document.querySelector("#manage-property-screen");



// close user form with button
if (closeUserForm !== null) {
    closeUserForm.addEventListener("click", function () {
        userFormContainer.style.display = "none";
        location.reload();
    });
}




// click outside to close user form
window.onclick = function (event) {
    if (event.target === userFormContainer) {
        userFormContainer.style.display = "none";
        location.reload();
    }
};



// change role value
if (toggleSwitch) {
    toggleSwitch.addEventListener('change', () => {
        if (toggleSwitch.checked) {
            role.checked = false;
            role.setAttribute('name', '');
            toggleSwitch.setAttribute('name','role');

        } else {
            role.checked =true;
            role.setAttribute('name', 'role');
            toggleSwitch.setAttribute('name','');
        }

    });
}



// user edit/delete
const editUserButton = document.querySelectorAll(".edit-user");
const deleteUserButton = document.querySelectorAll(".delete-user");
if(editUserButton.length>0|| deleteUserButton.length>0){

    // edit user
    editUserButton.forEach(button =>{
        const userData = button.parentElement.parentElement.children;
        const id = document.querySelector("#id");
        const firstName = document.querySelector("#firstName");
        const lastName = document.querySelector("#lastName");
        const email = document.querySelector("#email");

        const statusID = document.querySelector("#status-id");
        button.addEventListener("click", function () {

            //set heading
            document.querySelector("#user-form-heading").innerHTML = "Update User";

            // change form action
            userForm.action = "/update-user";

            // populate form
            id.value = statusID.value = userData[1].value;
            lastName.value = (userData[0].children[0].innerHTML).slice(0, -1);
            firstName.value = userData[0].children[1].innerHTML;
            email.value = userData[2].value;

            //hide email field
            document.querySelector("#label-and-email-field").style.display = "none";
            //set role value
            if (userData[3].innerHTML === "ADMINISTRATOR") {
                toggleSwitch.checked = true;
            }

            //show suspend or enable button
            changeStatusButton.innerHTML = userData[4].innerHTML === "Active" ? "Suspend" : "Activate";
            changeStatusButton.style.display = "inline-block";

            // set submit button text
            document.querySelector("#user-form-submit").innerHTML = "Update User";
            // show form
            userFormContainer.style.display = "block";


        });


    });

    // activate/suspend
    if (changeStatusButton !== null) {
        changeStatusButton.addEventListener("click", () => {
            // show confirmation window
            confirmationHeading.innerHTML = `${changeStatusButton.innerHTML}`;
            confirmationQuestion.innerHTML = `This action will ${changeStatusButton.innerHTML.toLowerCase()} this account. Do you want to continue?`;
            statusConfirmationButton.style.display = "block";
            confirmationWindow.style.display = "block";
        });
    }




    // delete user
    deleteUserButton.forEach(button =>{
        button.addEventListener("click", function () {
            const deleteID = document.querySelector("#delete-id");
            const userData = button.parentElement.parentElement.children;
            const firstName = button.parentElement.parentElement.children[0].children[1].innerHTML;
            const lastName = button.parentElement.parentElement.children[0].children[0].innerHTML.slice(0, -1);
            // show confirmation window
            confirmationHeading.innerHTML = "Delete";
            confirmationQuestion.innerHTML = `This action is permanent. Do you want to delete record for '${firstName} ${lastName}'?`;
            deleteConfirmationButton.style.display = "block";
            deleteID.value = userData[1].value;
            confirmationWindow.style.display = "block";
            });
    });




    // confirmation window close
    if (closeConfirmationWindow !== null) {
        closeConfirmationWindow.addEventListener("click", function(){
            confirmationWindow.style.display = "none";
        });
    }


    //toggle admin panel screens
    if (manageUserButton !== null) {
        manageUserButton.addEventListener("click", function () {
            manageUserScreen.style.display = "block";
            manageEquipmentScreen.style.display = "none";
            managePropertyScreen.style.display = "none";
        });
    }
    if (manageEquipmentButton !== null) {
        manageEquipmentButton.addEventListener("click", function () {
            manageUserScreen.style.display = "none";
            manageEquipmentScreen.style.display = "block";
            managePropertyScreen.style.display = "none";
        });
    }
    if (managePropertyButton !== null) {
        managePropertyButton.addEventListener("click", function () {
            manageUserScreen.style.display = "none";
            manageEquipmentScreen.style.display = "none";
            managePropertyScreen.style.display = "block";
        });
    }
}
