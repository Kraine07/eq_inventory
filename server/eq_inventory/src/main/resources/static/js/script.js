



// buttons
const manageUserButton = document.querySelector("#manage-user-btn");
const manageEquipmentButton = document.querySelector("#manage-equipment-btn");
const managePropertyButton = document.querySelector("#manage-property-btn");
const manageReportButton = document.querySelector("#manage-report-btn");
const manageManufacturerButton = document.querySelector("#manage-manufacturer-btn");


// screens
const manageUserScreen = document.querySelector("#manage-user-screen");
const manageEquipmentScreen = document.querySelector("#manage-equipment-screen");
const managePropertyScreen = document.querySelector("#manage-property-screen");
const manageReportScreen = document.querySelector("#manage-report-screen");
const manageManufacturerScreen = document.querySelector("#manage-manufacturer-screen");

//confirmation window
export const confirmationWindow = document.querySelector("#confirm-window");
export const confirmationQuestion = document.querySelector("#confirm-question");
export const confirmationHeading = document.querySelector("#confirm-heading");
export const closeConfirmationWindow = document.querySelector("#confirm-close");
export const statusConfirmationButton = document.querySelector("#status-confirm-btn");
export const confimationNoButton = document.querySelector("#confirm-no");


// clear active screen from local storage if login form is loaded
if (document.querySelector(("#login-form"))) {
    localStorage.removeItem("activeScreen");
}


// handle refresh to same screen
const screenMap = {};
// create entries only if respective screen and button are present
if (manageUserScreen && manageUserButton)
    screenMap["manage-user-screen"] = { screen: manageUserScreen, button: manageUserButton };
if (manageEquipmentScreen && manageEquipmentButton)
    screenMap["manage-equipment-screen"] = { screen: manageEquipmentScreen, button: manageEquipmentButton };
if (manageManufacturerScreen && manageManufacturerButton)
    screenMap["manage-manufacturer-screen"] = { screen: manageManufacturerScreen, button: manageEquipmentButton };
if (managePropertyScreen && managePropertyButton)
    screenMap["manage-property-screen"] = { screen: managePropertyScreen, button: managePropertyButton };
if (manageReportScreen && manageReportButton)
    screenMap["manage-report-screen"] = { screen: manageReportScreen, button: manageReportButton };


const defaultScreen = "manage-equipment-screen";
const activeScreenId = localStorage.getItem("activeScreen") || defaultScreen;

//
Object.keys(screenMap).forEach(screen => {
    screenMap[screen].screen.classList.add("hidden");
    screenMap[screen].button.classList.remove("border-2", "border-color-4",  "text-color-4");
});


if (screenMap[activeScreenId]) {
    // If the active screen is defined in the screenMap, show it
    screenMap[activeScreenId].screen.classList.remove("hidden");
    screenMap[activeScreenId].button.classList.add("border-2", "border-color-4", "text-color-4");
}




// show user management screen
if (manageUserButton !== null) {
    manageUserButton.addEventListener("click", function () {
        localStorage.setItem("activeScreen", "manage-user-screen");

        manageManufacturerScreen.classList.add("hidden");
        manageUserScreen.classList.remove("hidden");
        manageEquipmentScreen.classList.add("hidden");
        managePropertyScreen.classList.add("hidden");
        manageReportScreen.classList.add("hidden");
        manageUserButton.classList.add("border-2", "border-color-4",  "text-color-4");
        manageEquipmentButton.classList.remove("border-2", "border-color-4",  "text-color-4");
        if(managePropertyButton) managePropertyButton.classList.remove("border-2", "border-color-4",  "text-color-4");
        manageReportButton.classList.remove("border-2", "border-color-4",  "text-color-4");
    });
}

// show equipment management screen
if (manageEquipmentButton !== null) {
    manageEquipmentButton.addEventListener("click", function () {
        localStorage.setItem("activeScreen", "manage-equipment-screen");

        manageManufacturerScreen.classList.add("hidden");
        manageEquipmentScreen.classList.remove("hidden");
        manageUserScreen.classList.add("hidden");
        managePropertyScreen.classList.add("hidden");
        manageReportScreen.classList.add("hidden");
        if(manageUserButton) manageUserButton.classList.remove("border-2", "border-color-4",  "text-color-4");
        manageEquipmentButton.classList.add("border-2", "border-color-4",  "text-color-4");
        if(managePropertyButton) managePropertyButton.classList.remove("border-2", "border-color-4",  "text-color-4");
        manageReportButton.classList.remove("border-2", "border-color-4",  "text-color-4");
    });
}

// show manufacturer management screen
if (manageManufacturerButton !== null) {
    manageManufacturerButton.addEventListener("click", function () {
        localStorage.setItem("activeScreen", "manage-manufacturer-screen");

        manageManufacturerScreen.classList.remove("hidden");
        manageUserScreen.classList.add("hidden");
        manageEquipmentScreen.classList.add("hidden");
        managePropertyScreen.classList.add("hidden");
        manageReportScreen.classList.add("hidden");
        if(manageUserButton) manageUserButton.classList.remove("border-2", "border-color-4",  "text-color-4");
        manageEquipmentButton.classList.add("border-2", "border-color-4",  "text-color-4");
        if(managePropertyButton) managePropertyButton.classList.remove("border-2", "border-color-4",  "text-color-4");
        manageReportButton.classList.remove("border-2", "border-color-4",  "text-color-4");
    });
}

//show property management screen
if (managePropertyButton !== null) {
    managePropertyButton.addEventListener("click", function () {
        localStorage.setItem("activeScreen", "manage-property-screen");

        manageManufacturerScreen.classList.add("hidden");
        manageUserScreen.classList.add("hidden");
        manageEquipmentScreen.classList.add("hidden");
        managePropertyScreen.classList.remove("hidden");
        manageReportScreen.classList.add("hidden");
        if(manageUserButton) manageUserButton.classList.remove("border-2", "border-color-4",  "text-color-4");
        manageEquipmentButton.classList.remove("border-2", "border-color-4",  "text-color-4");
        managePropertyButton.classList.add("border-2", "border-color-4",  "text-color-4");
        manageReportButton.classList.remove("border-2", "border-color-4",  "text-color-4");
    });
}

//show reporting screen
if (manageReportButton !== null) {
    manageReportButton.addEventListener("click", function () {
        localStorage.setItem("activeScreen", "manage-report-screen");

        manageManufacturerScreen.classList.add("hidden");
        manageUserScreen.classList.add("hidden");
        manageEquipmentScreen.classList.add("hidden");
        managePropertyScreen.classList.add("hidden");
        manageReportScreen.classList.remove("hidden");
        if (manageUserButton) manageUserButton.classList.remove("border-2", "border-color-4", "text-color-4");
        manageEquipmentButton.classList.remove("border-2", "border-color-4", "text-color-4");
        if (managePropertyButton) managePropertyButton.classList.remove("border-2", "border-color-4", "text-color-4");
        manageReportButton.classList.add("border-2", "border-color-4", "text-color-4");
    });

}

// close confimation window
document.addEventListener("click", function (event) {
    if (event.target === confirmationWindow || event.target === confimationNoButton || closeConfirmationWindow.contains(event.target)) {
        // return scrolling
        document.body.classList.remove('overflow-hidden');
        confirmationWindow.style.display = "none";
    }
});
