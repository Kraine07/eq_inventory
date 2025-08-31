

import { confirmationQuestion } from "./script.js";
import { confirmationHeading } from "./script.js";
import { confirmationWindow } from "./script.js";
import { statusConfirmationButton } from "./script.js";
import { deleteConfirmationButton } from "./script.js";

const userForm = document.getElementById("user-form");
const userFormContainer = document.getElementById("user-form-container");
const role = document.getElementById("role");
const toggleSwitch = document.getElementById("toggleSwitch");
const closeUserForm = document.querySelector("#close-user-form-btn");
const openUserForm = document.querySelector("#open-user-form-btn");


const userStatusButton = document.querySelectorAll(".user-status")

// user edit/delete
const editUserButton = document.querySelectorAll(".edit-user");
const deleteUserButton = document.querySelectorAll(".delete-user");






// click to close
document.addEventListener("click", function (event) {

    //click outside userform
    if (userFormContainer || closeUserForm) {
        if( event.target === userFormContainer || closeUserForm.contains(event.target)) {
            document.body.classList.remove('overflow-hidden');
            userFormContainer.style.display = "none";
            userForm.reset();
        }
    }
    

});


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


// open user form
if (openUserForm !== null) {
    openUserForm.addEventListener("click", function () {

        //set form action
        userForm.action = "/app/register";

        // set submit button & heading text
        document.querySelector("#user-form-heading").innerHTML = "Create User";
        document.querySelector("#user-form-submit").innerHTML = "Create User";
        // show form
        window.scrollTo({ top: 0, behavior: "smooth" });
        document.body.classList.add('overflow-hidden');
        userFormContainer.style.display = "block";
    })
}


//edit & delete
if(editUserButton.length>0 || deleteUserButton.length>0 ||userStatusButton.length>0){

    // edit user
    editUserButton.forEach(editButton => {

        // place table row data in form
        const userRow = editButton.closest("tr").children;
        const userId = document.querySelector("#user-id");

        const firstName = document.querySelector("#firstName");
        const lastName = document.querySelector("#lastName");
        const email = document.querySelector("#email");

        const [lastNameText, firstNameText] = userRow[1].textContent.trim().split(", ");

        const statusID = document.querySelector("#status-id");
        editButton.addEventListener("click", function () {

            //set heading
            document.querySelector("#user-form-heading").innerHTML = "Update User";

            // change form action
            userForm.action = "/update-user";

            // populate form
            userId.value = statusID.value = userRow[0].textContent;
            lastName.value = lastNameText;
            firstName.value = firstNameText;
            email.value = userRow[2].textContent;

            //hide email field
            document.querySelector("#label-and-email-field").style.display = "none";
            //set role value
            if (userRow[3].innerHTML === "ADMINISTRATOR") {
                toggleSwitch.checked = true;
            }

            // set submit button & heading text
            document.querySelector("#user-form-heading").innerHTML = "Update User";
            document.querySelector("#user-form-submit").innerHTML = "Update User";

            // prevent scrolling
            document.body.classList.add('overflow-hidden');
            window.scrollTo({ top: 0, behavior: "smooth" });

            // show form
            userFormContainer.style.display = "block";


        });


    });

    // activate/suspend

    userStatusButton.forEach(statusButton => {
        statusButton.addEventListener("click", () => {
            const userStatus = document.querySelector("#user-status");
            const statusId = document.querySelector("#status-id");

            statusId.value = statusButton.parentElement.parentElement.parentElement.children[0].textContent;

            statusButton.setAttribute("form","status-form")
            confirmationHeading.textContent = "Status Update";
            confirmationQuestion.textContent = `This action will change account status to ${ userStatus.textContent == 'Active' ? 'Suspended':'Active'}. Do you want to proceed?`

            deleteConfirmationButton.style.display = "none";

            // prevent scrolling
            document.body.classList.add('overflow-hidden');
            window.scrollTo({ top: 0, behavior: "smooth" });

            // show confirmation window
            statusConfirmationButton.style.display = "block";
            confirmationWindow.style.display = "block";
        });
    });





    // delete user
    deleteUserButton.forEach(deleteButton =>{
        deleteButton.addEventListener("click", function () {

            const deleteID = document.querySelector("#delete-id");
            const userRow = deleteButton.parentElement.parentElement.parentElement.children;
            const fullName = userRow[1].textContent;
            const [lastName, firstName] = fullName.split(", ").map(name => name.charAt(0).toUpperCase() + name.slice(1).toLowerCase().trim());

            deleteConfirmationButton.setAttribute("form","delete-user")
            // const lastName = deleteButton.parentElement.parentElement.children[0].children[0].innerHTML.slice(0, -1);
            // show confirmation window
            confirmationHeading.innerHTML = "Delete Confirmation";
            confirmationQuestion.innerHTML = `Deleting this account is permanent. Please confirm deletion of '${firstName} ${lastName}'?`;
            statusConfirmationButton.style.display = "none";
            deleteConfirmationButton.style.display = "block";
            deleteID.value = userRow[0].textContent;
            // prevent scrolling
            document.body.classList.add('overflow-hidden');
            window.scrollTo({ top: 0, behavior: "smooth" });
            confirmationWindow.style.display = "block";
            });
    });




}

