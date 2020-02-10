# Api Printer JasperSoft with Spring Boot
A simple api to send jasperreports to the printer
## How to Use
Rename the config.properties.expample to config.properties.

Rename the conexion.java.example to conexion.java and make your connection.

Edit the file and specify the parameters
```
url=localhost //Your database url
port=3306 //Your database port
database=db //database name
user=root
pass=admin
reportFolder=reports //The reports Folder
imageFolder=reports //Report Images folder
```
Run the project

Go to http://localhost:5000/printer to list your local printers

Send a post json like
```
$.ajax({
  url:"http://localhost:5000/printer",
  type:"post",
  contentType: "application/json; charset=utf-8",
  dataType: "json",
  data:JSON.stringify({
    subreport: "subreportname.jasper", //subreport parameter example
    report: "reportort.jasper",
    printer: "printername"
    another: "Another Report Parameter"
  }),
  success:function(data){
    data
  }
});
```
Enjoy your printing ☺
