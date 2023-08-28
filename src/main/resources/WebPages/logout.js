function logout() {
  const response = confirm("Are you sure you want to do that?");

  if (response) {
    window.location.href = "./LoginPage.html";
    console.log("Ok was pressed");
  } else {
    console.log("Cancel was pressed");
  }
}
