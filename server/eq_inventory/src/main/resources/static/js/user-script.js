


const userForm = document.getElementById("user-form");
const userFormContainer = document.getElementById("user-form-container");
const role = document.getElementById("role");
const toggleSwitch = document.getElementById("toggleSwitch");
const closeUserForm = document.querySelector("#close-user-form-btn");
const openUserForm = document.querySelector("#open-user-form-btn");

const confirmationWindow = document.querySelector("#confirm-window");
const confirmationQuestion = document.querySelector("#confirm-question");
const confirmationHeading = document.querySelector("#confirm-heading");
const closeConfirmationWindow = document.querySelector("#confirm-close");
const deleteConfirmationButton = document.querySelector("#delete-confirm-btn");
const statusConfirmationButton = document.querySelector("#status-confirm-btn");

const changeStatusButton = document.querySelector("#change-status-btn");

// user edit/delete
const editUserButton = document.querySelectorAll(".edit-user");
const deleteUserButton = document.querySelectorAll(".delete-user");




// close user form with button
if (closeUserForm !== null) {
    closeUserForm.addEventListener("click", function () {
        document.body.classList.remove('overflow-hidden');
        userFormContainer.style.display = "none";
        userForm.reset();
    });
}


// click outside user form to close
document.addEventListener("click", function (event) {
    if( event.target === userFormContainer) {
        document.body.classList.remove('overflow-hidden');
        userFormContainer.style.display = "none";
        userForm.reset();
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
if(editUserButton.length>0|| deleteUserButton.length>0){

    // edit user
    editUserButton.forEach(editButton => {

        // place table row data in form
        const userRow = editButton.closest("tr").children;
        const userId = document.querySelector("#user-id");

        const firstName = document.querySelector("#firstName");
        const lastName = document.querySelector("#lastName");
        const email = document.querySelector("#email");

        const [firstNameText, lastNameText] = userRow[1].textContent.trim().split(" ");

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

            //show suspend or enable button
            changeStatusButton.value = userRow[4].children[0].innerHTML === "Active" ? "Suspend" : "Activate";
            changeStatusButton.classList.add(userRow[4].children[0].innerHTML === "Active" ? "bg-color-4" : "bg-color-3");
            changeStatusButton.style.display = "inline-block";

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
    deleteUserButton.forEach(deleteButton =>{
        deleteButton.addEventListener("click", function () {
            console.log("delete")
            const deleteID = document.querySelector("#delete-id");
            const userRow = deleteButton.parentElement.parentElement.parentElement.children;
            const fullName = userRow[1].textContent;
            // const lastName = deleteButton.parentElement.parentElement.children[0].children[0].innerHTML.slice(0, -1);
            // show confirmation window
            confirmationHeading.innerHTML = "Delete";
            confirmationQuestion.innerHTML = `Deleting this account is permanent. Confirm deletion of '${fullName}'?`;
            deleteConfirmationButton.style.display = "block";
            deleteID.value = userRow[0].textContent;
            confirmationWindow.style.display = "block";
            });
    });


    // confirmation window close
    if (closeConfirmationWindow !== null) {
        closeConfirmationWindow.addEventListener("click", function(){
            confirmationWindow.style.display = "none";
        });
    }

}

