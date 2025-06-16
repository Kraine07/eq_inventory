

// buttons
const manageUserButton = document.querySelector("#manage-user-btn");
const manageEquipmentButton = document.querySelector("#manage-equipment-btn");
const managePropertyButton = document.querySelector("#manage-property-btn");
const manageReportButton = document.querySelector("#manage-report-btn");

// screens
const manageUserScreen = document.querySelector("#manage-user-screen");
const manageEquipmentScreen = document.querySelector("#manage-equipment-screen");
const managePropertyScreen = document.querySelector("#manage-property-screen");
const manageReportScreen = document.querySelector("#manage-report-screen");



// show user management screen
if (manageUserButton !== null) {
    manageUserButton.addEventListener("click", function () {
        manageUserScreen.classList.remove("hidden");
        manageEquipmentScreen.classList.add("hidden");
        managePropertyScreen.classList.add("hidden");
        manageReportScreen.classList.add("hidden");
        manageUserButton.classList.add("border-b-2", "border-b-color-4", "rounded-b-none", "text-color-4");
        manageEquipmentButton.classList.remove("border-b-2", "border-b-color-4", "rounded-b-none", "text-color-4");
        managePropertyButton.classList.remove("border-b-2", "border-b-color-4", "rounded-b-none", "text-color-4");
        manageReportButton.classList.remove("border-b-2", "border-b-color-4", "rounded-b-none", "text-color-4");
    });
}

// show equipment management screen
if (manageEquipmentButton !== null) {
    manageEquipmentButton.addEventListener("click", function () {

        manageUserScreen.classList.add("hidden");
        manageEquipmentScreen.classList.remove("hidden");
        managePropertyScreen.classList.add("hidden");
        manageReportScreen.classList.add("hidden");
        manageUserButton.classList.remove("border-b-2", "border-b-color-4", "rounded-b-none", "text-color-4");
        manageEquipmentButton.classList.add("border-b-2", "border-b-color-4", "rounded-b-none", "text-color-4");
        managePropertyButton.classList.remove("border-b-2", "border-b-color-4", "rounded-b-none", "text-color-4");
        manageReportButton.classList.remove("border-b-2", "border-b-color-4", "rounded-b-none", "text-color-4");
    });
}

//show property management screen
if (managePropertyButton !== null) {
    managePropertyButton.addEventListener("click", function () {
        manageUserScreen.classList.add("hidden");
        manageEquipmentScreen.classList.add("hidden");
        managePropertyScreen.classList.remove("hidden");
        manageReportScreen.classList.add("hidden");
        manageUserButton.classList.remove("border-b-2", "border-b-color-4", "rounded-b-none", "text-color-4");
        manageEquipmentButton.classList.remove("border-b-2", "border-b-color-4", "rounded-b-none", "text-color-4");
        managePropertyButton.classList.add("border-b-2", "border-b-color-4", "rounded-b-none", "text-color-4");
        manageReportButton.classList.remove("border-b-2", "border-b-color-4", "rounded-b-none", "text-color-4");
    });
}

//show reporting screen
if (manageReportButton !== null) {
    manageReportButton.addEventListener("click", function () {
        manageUserScreen.classList.add("hidden");
        manageEquipmentScreen.classList.add("hidden");
        managePropertyScreen.classList.add("hidden");
        manageReportScreen.classList.remove("hidden");
        manageUserButton.classList.remove("border-b-2", "border-b-color-4", "rounded-b-none", "text-color-4");
        manageEquipmentButton.classList.remove("border-b-2", "border-b-color-4", "rounded-b-none", "text-color-4");
        managePropertyButton.classList.remove("border-b-2", "border-b-color-4", "rounded-b-none", "text-color-4");
        manageReportButton.classList.add("border-b-2", "border-b-color-4", "rounded-b-none", "text-color-4");
    });
}