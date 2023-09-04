function logout() {
  const response = confirm("Are you sure you want to log out?");

  if (response) {
    window.location.href = "https://nirajt03.github.io/sample-website/";
    console.log("Ok was pressed");
  } else {
    console.log("Cancel was pressed");
  }
}
