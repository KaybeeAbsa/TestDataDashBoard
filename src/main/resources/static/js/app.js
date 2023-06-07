var BASE_HREF = '/TestDataDashBoard/';

var testDataDashBoardModule = angular.module("TestDataDashBoardApp", ['ngRoute']);
testDataDashBoardModule.config(["$routeProvider", function ($routeProvider) {
    $routeProvider
        .when('/login', {
            templateUrl: '/login.html',
            controller: 'LoginController'
        }).when('/home', {
        templateUrl: '/home.html',
        controller: 'TestDataController'
    }).when('/contact', {
        templateUrl: '/contact.html',
        controller: 'ContactController'
    }).when('/about', {
        templateUrl: '/about.html',
        controller: 'TestDataController'
    }).when('/specialRequest', {
        templateUrl: '/specialRequest.html',
        controller: 'SpecialRequestController'
    }).when('/testCard', {
        templateUrl: '/testCard.html',
        controller: 'TestCardController'
          });
      }]);

//function for excel
var tableToExcel = (function () {
    var uri = 'data:application/vnd.ms-excel;base64,',
        template = '<html xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:x="urn:schemas-microsoft-com:office:excel" xmlns="http://www.w3.org/TR/REC-html40"><head><!--[if gte mso 9]><xml><x:ExcelWorkbook><x:ExcelWorksheets><x:ExcelWorksheet><x:Name>{worksheet}</x:Name><x:WorksheetOptions><x:DisplayGridlines/></x:WorksheetOptions></x:ExcelWorksheet></x:ExcelWorksheets></x:ExcelWorkbook></xml><![endif]--></head><body><table>{table}</table></body></html>',
        base64 = function (s) {
            return window.btoa(unescape(encodeURIComponent(s)))
        }, format = function (s, c) {
            return s.replace(/{(\w+)}/g, function (m, p) {
                return c[p];
            })
        }
    return function (table, name, filename) {

        if (!table.nodeType) table = document.getElementById(table)
        table.oldHTML = table.innerHTML;
        table.innerHTML = table.oldHTML;
        var ctx = {
            worksheet: name || 'Worksheet',
            table: table.innerHTML
        }
        document.getElementById("dlink").href = uri + base64(format(template, ctx));
        document.getElementById("dlink").download = filename;
        document.getElementById("dlink").click();



        window.location.reload();
        window.setInterval(500)
    }

})()

testDataDashBoardModule.controller("LoginController", function ($scope, $http, $location) {
    $http.defaults.headers.post["Content-Type"] = "application/json";

    $scope.login = function () {
        if ($scope.authantication.$valid) {
            var username = $scope.username;
            var password = $scope.password;

            $location.url('home')

        } else {
            $scope.submitted = true;
        }
    }
})

testDataDashBoardModule.factory("UserService", function ($http) {
    $http.defaults.headers.post["Content-Type"] = "application/json";
    var service = {};
    service.getUser = GetUser;
    return service;
    function GetUser() {
        return $http.get(BASE_HREF + 'user').then(function (response) { return response.data });
    }
})


testDataDashBoardModule.controller("PhysicalTestCardController", function ($scope, $http, UserService) {
    $http.defaults.headers.post["Content-Type"] = "application/json";

    $("#warning-alert").hide();
    $("#danger-alert").hide();

    initController();

    function initController() {
        // Request User data from backend
        $scope.pathurl =  window.location.pathname;
        UserService.getUser()
            .then(function (data) {
                $scope.rname = data.fullName;
                $scope.email = data.mail;

            })
            .catch(function (error) {
                if (error.data && error.data.message) {
                    alert(error.data.message)
                    // window.location.reload();
                }
                console.error('Error', error)
            });
    };

    //######################## NewToBank #####################################
    $scope.checkemail = function (){

        var email = $scope.email;

        if(email == 'Karabo.Serope@absa.africa' || email == 'Mahlodi.Monyela@absa.africa' || email == 'Peter.James@absa.africa'){
            // alert('inside email');
            //  document.getElementById("ViewAllCaptured").style.visibility = "hidden";
        }else{
            //alert('outside email');
            document.getElementById("ViewAllCaptured").style.visibility = "hidden";
        }
    };

    $scope.request = function () {


        if ($scope.existing.$valid) {
            var reName = $scope.rname;
            var reTeam = $scope.tname;
            var reMail = $scope.email;
            var reMobile = $scope.cellNo;
            var request ="newAccounts";
            var scoreV = $scope.selectedScore;
            var quantity = $scope.selectedQuantity;
            var selectedPro = $scope.selectedProduct;
            var selectedEnv = $scope.selectedEnvironment;
            var selectedProCode = $scope.selectedProductCode;
            var selectedCombiNum = $scope.selectedCombi;
            var selectedTrans = $scope.selectedTransaction;
            var selectedPre = $scope.selectedPreApproved;
            var used = "NO";
            var clientType = $scope.selectedClientType;
            var selectedOverDraft = $scope.selectedAddOverDraft;

            var reEditInput = $scope.existingData;
            var reEditClientType = $scope.selectEditClientType;
            var reEditType = $scope.selectEditType;
            var reEditProduct = $scope.editProduct;
            var reEditTransactionType = $scope.selectedTransactionType;
            var reTransactionAmount = $scope.transactionAmount;

            var brand = $scope.selectedProductBrand;
            //linked

            var linkCheque = $scope.LCheques;
            var linkSavings = $scope.LSavings;
            var linkCard = $scope.LCreditCard;
            var linkPl = $scope.LPl;
            var linkHml = $scope.LHml;
            var linkAvaf = $scope.LAvaf;

            if (linkCheque == undefined) {
                linkCheque = "no";
            } else if (linkSavings == undefined) {
                linkSavings = "no";
            } else if (linkPl == undefined) {
                linkPl == "no"
            } else if (linkCard == undefined) {
                linkCard = "no";
            } else if (linkHml == undefined) {
                linkHml = "no"
            } else if (linkAvaf == undefined) {
                linkAvaf = "no";
            }

            //disable button submit after click
            var btnId = document.getElementById('btnSubmit');
            btnId.disabled = true;

            $scope.loading = true;
            $("#loadingModal").show();

            if (request === "Ntb") {
                var Data = {};
                window.location.search.replace(/\?/, '').split('&').map(function (o) { Data[o.split('=')[0]] = o.split('=')[1]; });

                $http.get(BASE_HREF + 'ntb/ntbclients/' + quantity + '/' + scoreV + '/' + used + '/' + reName + '/' + reTeam + '/' + reMail + '/' + reMobile + '').then(function (response) {
                    $scope.NtbTestData = response.data;

                    //enable and disable download button
                    $scope.buttonShow = false;
                    $scope.loading = false;
                    $("#loadingModal").hide();

                    if (!Object.keys(response.data).length) {
                        $scope.buttonShow = false;

                    } else {
                        $scope.buttonShow = true;
                        $("#successModal").show();
                    }

                }).catch(function (error) {

                    $scope.loading = false;
                    $("#loadingModal").hide();

                    $scope.dangerMessage = error.data.message;
                  //  alert(error.data.message);
                   $("#danger-alert").show();

                });

            } else if (request === "Existing") {

                if (selectedPro === "ExAvaf" || selectedPro === "ExMln" || selectedPro === "ExPL") {
                    selectedCombiNum = "NO";
                    selectedPre = "NO";
                }

                $http.get(BASE_HREF + 'existing/findclient/' + quantity + '/' + used + '/' + reName + '/' + reTeam + '/' + reMail + '/' + reMobile + '/' + selectedEnv + '/' + scoreV + '/' + selectedPro + '/' + selectedProCode + '/' + selectedCombiNum + '/' + selectedPre + '/' + selectedOverDraft + '/' + clientType + '/' + linkCheque + '/' + linkSavings + '/' + linkCard + '/' + linkPl + '/' + linkHml + '/' + linkAvaf + '').then(function (response) {
                    $scope.NtbTestData = response.data;

                    //enable and disable download button
                    $scope.buttonShow = false;
                    $scope.loading = false;
                    $("#loadingModal").hide();

                    if (!Object.keys(response.data).length) {
                        $scope.buttonShow = false;
                    } else {
                        $scope.buttonShow = true;

                        $("#successModal").show();
                    }

                }).catch(function (error) {
                    $scope.loading = false;
                    $("#loadingModal").hide();

                    $scope.dangerMessage = error.data.message;
                    //  alert(error.data.message);
                    $("#danger-alert").show();
                });;

            } else if (request === "newAccounts") {

                $http.get(BASE_HREF + 'testCard/createNewClient/' + quantity + '/' + used + '/' + reName + '/' + reTeam + '/' + reMail + '/' + reMobile + '/' + selectedEnv + '/' + scoreV + '/' + selectedPro + '/' + clientType + '/' + selectedProCode + '/' + selectedCombiNum + '/' + selectedTrans + '/'+ brand + '').then(function (response) {
                    $scope.NtbTestData = response.data;

                    //enable and disable download button
                    $scope.buttonShow = false;
                    $scope.loading = false;
                    $("#loadingModal").hide();
                    if (!Object.keys(response.data).length) {
                        $scope.buttonShow = false;
                    } else {
                        $scope.buttonShow = true;
                         $("#successModal").show();
                    }

                }).catch(function (error) {
                    $scope.loading = false;
                    $("#loadingModal").hide();

                    $scope.dangerMessage = error.data.message;
                    //  alert(error.data.message);
                    $("#danger-alert").show();
                });;
            } else if (request === "editClients") {

                $http.get(BASE_HREF + 'EditExistingClients/EditClients/' + reName + '/' + reTeam + '/' + reMail + '/' + reMobile + '/' + selectedEnv + '/' + '/' + reEditClientType + '/' + '/' + reEditType + '/' + reEditInput + '/' + reEditProduct + '/' + selectedPro + '/' + selectedProCode + '/' + selectedCombiNum + '/' + reEditTransactionType + '/' + reTransactionAmount + '/' + selectedTrans + '').then(function (response) {
                    $scope.NtbTestData = response.data;
                    //enable and disable download button
                    //enable and disable download button
                                       $scope.buttonShow = false;
                                       $scope.loading = false;
                                       $("#loadingModal").hide();
                                       if (!Object.keys(response.data).length) {
                                           $scope.buttonShow = false;
                                       } else {
                                           $scope.buttonShow = true;
                                            $("#successModal").show();
                                       }

                                   }).catch(function (error) {
                    $scope.loading = false;
                    $("#loadingModal").hide();

                    $scope.dangerMessage = error.data.message;
                    //  alert(error.data.message);
                    $("#danger-alert").show();
                                   });;
            }

        } else {
            $scope.submitted = true;
        }
    };
});


testDataDashBoardModule.controller("TestDataController", function ($scope, $http, $location,UserService) {
    $http.defaults.headers.post["Content-Type"] = "application/json";

    $("#warning-alert").hide();
    $("#danger-alert").hide();

    initController();

    $scope.switchToHome = function (){
     //   alert("Hello home");
        $location.url('home');
    };

    $scope.switchToHomePage = function (){
      // alert("Hello home page");
        $location.url('homepage')
    };

    function initController() {
        // Request User data from backend
        $scope.pathurl =  window.location.pathname;
        UserService.getUser()
            .then(function (data) {
                $scope.rname = data.fullName;
                $scope.email = data.mail;

            })
            .catch(function (error) {
                if (error.data && error.data.message) {
                    alert(error.data.message)
                    // window.location.reload();
                }
                console.error('Error', error)
            });
    };

    $scope.checkemail = function (){

        var email = $scope.email;

        if(email == 'Karabo.Serope@absa.africa' || email == 'Mahlodi.Monyela@absa.africa' || email == 'Peter.James@absa.africa'){
            // alert('inside email');
            //  document.getElementById("ViewAllCaptured").style.visibility = "hidden";
        }else{
            //alert('outside email');
            document.getElementById("ViewAllCaptured").style.visibility = "hidden";
        }
    };

    //Main Function
    $scope.request = function () {

        if ($scope.existing.$valid) {


            var reName = $scope.rname;
            var reTeam = $scope.tname;
            var reMail = $scope.email;
            var reMobile = $scope.cellNo;
            var request = $scope.selectedRequest;
            var scoreV = $scope.selectedScore;
            var quantity = $scope.selectedQuantity;
            var selectedPro = $scope.selectedProduct;
            var selectedEnv = $scope.selectedEnvironment;
            var selectedProCode = $scope.selectedProductCode;
            var selectedCombiNum = $scope.selectedCombi;
            var selectedTrans = $scope.selectedTransaction;
            var selectedPre = $scope.selectedPreApproved;
            var reselectedCompliant = $scope.selectedCompliant;
            var used = "NO";
            var clientType = $scope.selectedClientType;
            var selectedOverDraft = $scope.selectedAddOverDraft;
            var selectedAddMandate = $scope.selectedAddMandate;
            var mandateAccountNo = $scope.mandateAccountNo;
            var reMandateIDNumber = $scope.mandateIDNumber;
            var mandateAccountType = $scope.mandateAccountType;
            var reMandateColltnAmt = $scope.mandateColltnAmt;
            var newToProductCheque = $scope.chequeNewToProduct;
            var newToProductSavings = $scope.savingsNewToProduct;

            var reEditInput = $scope.existingData;
            var reEditClientType = $scope.selectEditClientType;
            var reEditType = $scope.selectEditType;
            var reEditProduct = $scope.editProduct;
            var reEditTransactionType = $scope.selectedTransactionType;
            var reTransactionAmount = $scope.transactionAmount;

            //linked
            var linkCheque = $scope.LCheques;
            var linkSavings = $scope.LSavings;
            var linkCard = $scope.LCreditCard;
            var linkPl = $scope.LPl;
            var linkHml = $scope.LHml;
            var linkAvaf = $scope.LAvaf;

            if (linkCheque == undefined) {
                linkCheque = "NO";
            } else if (linkSavings == undefined) {
                linkSavings = "NO";
            } else if (linkPl == undefined) {
                linkPl == "NO"
            } else if (linkCard == undefined) {
                linkCard = "NO";
            } else if (linkHml == undefined) {
                linkHml = "NO"
            } else if (linkAvaf == undefined) {
                linkAvaf = "NO";
            }


            var idType = $scope.idTypeIdNumber;

            var ecasa = $scope.selectedCasa;
            var policy = $scope.selectedPolicy;

            var authType = $scope.authType;
            var authInputType = $scope.authAccountNo;

            var removeHoldType = $scope.removeholdType;
            var removeHoldInputType = $scope.removeholdAccountNo;

            var ficHold = $scope.selectedFicHold;
            var cifHold = $scope.selectedCifHold;


            //pre-assessed values
            var preClientCode  = $scope.preClientCode;
            var preSalary1  = $scope.preSalary1;
            var preSalary2  = $scope.preSalary2;
            var preSalary3 = $scope.preSalary3;
            var preAvarageSalary = $scope.preAvarageSalary;
            var preGrossSalary  = $scope.preGrossSalary;
            var preNetSalary = $scope.preSalaryNetSalary;

            //Manual Request

            var manualscore = $scope.selectedScoreManual;
            var manualQuantity = $scope.selectedQuantityManual;
            var manualProduct = $scope.selectedProductManual;
            var manualClientTpe = $scope.selectedClientTypeManual;
            var manualPreApproved = $scope.selectedPreApprovalManual;
            var manualCombi = $scope.selectedCombiNumberManual;
            var manualComment  = $scope.commentmanual;


           // alert(reselectedCompliant);

            //disable button submit after click
            var btnId = document.getElementById('btnSubmit');
            btnId.disabled = true;

            $scope.loading = true;
            $("#loadingModal").show();

            if (request === "Ntb") {

                var scoreNTB = $scope.selectedScoreNTB;
                var quantityNTB = $scope.selectedQuantityNTB;

                var Data = {};
                window.location.search.replace(/\?/, '').split('&').map(function (o) { Data[o.split('=')[0]] = o.split('=')[1]; });

                $http.get(BASE_HREF + 'ntb/ntbclients/' + quantityNTB + '/' + scoreNTB + '/' + used + '/' + reName + '/' + reTeam + '/' + reMail + '/' + reMobile + '').then(function (response) {
                    $scope.NtbTestData = response.data;

                    //enable and disable download button
                    $scope.buttonShow = false;
                    $scope.loading = false;
                    $("#loadingModal").hide();

                    if (!Object.keys(response.data).length) {
                        $scope.buttonShow = false;

                    } else {
                        $scope.buttonShow = true;
                        $("#successModal").show();
                    }

                }).catch(function (error) {

                    $scope.loading = false;
                    $("#loadingModal").hide();

                    $scope.dangerMessage = error.data.message;
                    //  alert(error.data.message);
                    $("#danger-alert").show();

                });

            } else if(request   === "manualRequest"){
                $http.get(BASE_HREF + 'manualRequest/manual/' + manualQuantity + '/' + reName + '/' + reTeam + '/' + reMail + '/' + reMobile + '/' + selectedEnv + '/' + manualscore  + '/' + manualProduct + '/' + manualClientTpe + '/' + manualPreApproved + '/' + manualCombi + '/' + manualComment + '').then(function (response) {

                    $scope.NtbTestData = response.data;

                    //enable and disable download button
                    $scope.buttonShow = false;
                    $scope.loading = false;
                    $("#loadingModal").hide();

                    if (!Object.keys(response.data).length) {
                        $scope.buttonShow = false;
                    } else {
                        $scope.buttonShow = true;
                        if (response.data !== 0) {
                            var popup = document.getElementById("myPopup");
                            popup.classList.toggle("show");

                            setTimeout(function () { window.location.reload(); }, 3000);
                        }
                         //$("#successModal").show();
                    }

                }).catch(function (error) {
                    $scope.loading = false;
                    $("#loadingModal").hide();

                    $scope.dangerMessage = error.data.message;
                    //  alert(error.data.message);
                    $("#danger-alert").show();
                });;
            }else if ( request === "newtoproduct") {

                if (selectedPro === "ExAvaf" || selectedPro === "ExMln" || selectedPro === "ExPL") {
                    selectedCombiNum = "NO";
                    selectedPre = "NO";
                }

            $http.get(BASE_HREF + 'existing/findclient/' + quantity + '/' + used + '/' + reName + '/' + reTeam + '/' + reMail + '/' + reMobile + '/' + selectedEnv + '/' + scoreV + '/' + selectedPro + '/' + selectedProCode + '/' + 'no' + '/' + 'no' + '/' + 'no' + '/' + clientType + '/' + newToProductCheque + '/' + newToProductSavings + '/' + linkCard + '/' + linkPl + '/' + linkHml + '/' + linkAvaf + '/no' + '/no' + '/no'+'').then(function (response) {

                    $scope.NtbTestData = response.data;

                    //enable and disable download button
                    $scope.buttonShow = false;
                    $scope.loading = false;
                    $("#loadingModal").hide();

                    if (!Object.keys(response.data).length) {
                        $scope.buttonShow = false;
                    } else {
                        $scope.buttonShow = true;

                        $("#successModal").show();
                    }

                }).catch(function (error) {
                    $scope.loading = false;
                    $("#loadingModal").hide();

                    $scope.dangerMessage = error.data.message;
                    //  alert(error.data.message);
                    $("#danger-alert").show();
                });;

            } else if (request === "Existing" ) {

                var scoreExisting = $scope.selectedScoreExisting;
                var quantityExisting = $scope.selectedQuantityExisting;
                var productClientType = $scope.productClientType;

                if (selectedPro === "ExAvaf" || selectedPro === "ExMln" || selectedPro === "ExPL") {
                    selectedCombiNum = "NO";
                    selectedPre = "NO";
                }

                $http.get(BASE_HREF + 'existing/findclient/' + quantityExisting + '/' + used + '/' + reName + '/' + reTeam + '/' + reMail + '/' + reMobile + '/' + selectedEnv + '/' + scoreExisting + '/' + selectedPro + '/' + productClientType+ '/' + selectedCombiNum + '/' + selectedPre  + '/'+ reselectedCompliant + '/' + selectedOverDraft + '/' + clientType + '/' + linkCheque + '/' + linkSavings + '/' + linkCard + '/' + linkPl + '/' + linkHml + '/' + linkAvaf +'/'+policy+ '/'+ficHold+'/'+cifHold+'').then(function (response) {
                    $scope.NtbTestData = response.data;

                    //enable and disable download button
                    $scope.buttonShow = false;
                    $scope.loading = false;
                    $("#loadingModal").hide();

                    if (!Object.keys(response.data).length) {
                        $scope.buttonShow = false;
                    } else {
                        $scope.buttonShow = true;

                        $("#successModal").show();
                    }

                }).catch(function (error) {
                    $scope.loading = false;
                    $("#loadingModal").hide();

                    $scope.dangerMessage = error.data.message;
                    //  alert(error.data.message);
                    $("#danger-alert").show();
                });;

            } else if (request === "newAccounts") {

                var scoreNTB = $scope.selectedScoreNTB;
                var quantityNTB = $scope.selectedQuantityNTB;

                $http.get(BASE_HREF + 'clientCreation/createClient/' + quantityNTB + '/' + used + '/' + reName + '/' + reTeam + '/' + reMail + '/' + reMobile + '/' + selectedEnv + '/' + scoreNTB + '/' + selectedPro + '/' + idType + '/' + clientType + '/' + selectedProCode + '/' + selectedCombiNum + '/' + selectedTrans +'/'+policy+'/'+ficHold+'/'+cifHold+'').then(function (response) {
                    $scope.NtbTestData = response.data;

                    //enable and disable download button
                    $scope.buttonShow = false;
                    $scope.loading = false;
                    $("#loadingModal").hide();
                    if (!Object.keys(response.data).length) {
                        $scope.buttonShow = false;
                    } else {
                        $scope.buttonShow = true;
                        $("#successModal").show();
                    }

                }).catch(function (error) {
                    $scope.loading = false;
                    $("#loadingModal").hide();

                    $scope.dangerMessage = error.data.message;
                    //  alert(error.data.message);
                    $("#danger-alert").show();
                });;
            } else if (request === "editClients") {

                $http.get(BASE_HREF + 'EditExistingClients/EditClients/' + reName + '/' + reTeam + '/' + reMail + '/' + reMobile + '/' + selectedEnv + '/' + reEditClientType + '/' + reEditType + '/' + reEditInput + '/' + reEditProduct + '/' + selectedPro + '/' + selectedProCode + '/' + selectedCombiNum + '/' + reEditTransactionType + '/' + reTransactionAmount + '/' + selectedTrans + '/' + mandateAccountNo + '/' + mandateAccountType + '/' + reMandateIDNumber+ '/' + reMandateColltnAmt + '/' + authType + '/' + authInputType + '/' + removeHoldType + '/' + preClientCode + '/' + preSalary1 + '/' + preSalary2 + '/' + preSalary3 + '/' + preAvarageSalary + '/' + preGrossSalary + '/'+ preNetSalary + '/'+ removeHoldInputType + '').then(function (response) {
                    $scope.NtbTestData = response.data;
                    //enable and disable download button
                    //enable and disable download button
                    $scope.buttonShow = false;
                    $scope.loading = false;
                    $("#loadingModal").hide();
                    if (!Object.keys(response.data).length) {
                        $scope.buttonShow = false;
                    } else {
                        $scope.buttonShow = true;
                        $("#successModal").show();
                    }

                }).catch(function (error) {
                    $scope.loading = false;
                    $("#loadingModal").hide();

                    $scope.dangerMessage = error.data.message;
                    //  alert(error.data.message);
                    $("#danger-alert").show();
                });;
            }

        } else {
            $scope.submitted = true;
        }
    };
});

testDataDashBoardModule.controller("ContactController", function ($scope, $http, UserService) {
    $http.defaults.headers.post["Content-Type"] = "application/json";

    initController();

    function initController() {
        // Request User data from backend
        $scope.pathurl =  window.location.pathname;
        UserService.getUser()
            .then(function (data) {
                $scope.rname = data.userName;
                $scope.email = data.mail;

            })
            .catch(function (error) {
                if (error.data.message) {
                    alert(error.data.message)
                    // window.location.reload();
                }
                console.error('Error', error.data.message)
            });
    };

    //######################## NewToBank #####################################

    $scope.checkemail = function (){

        var email = $scope.email;

        if(email == 'Karabo.Serope@absa.africa' || email == 'Mahlodi.Monyela@absa.africa' || email == 'Peter.James@absa.africa'){
            // alert('inside email');
            //  document.getElementById("ViewAllCaptured").style.visibility = "hidden";
        }else{
            //alert('outside email');
            document.getElementById("ViewAllCaptured").style.visibility = "hidden";
        }
    };

    $scope.contactUs = function () {

        if ($scope.contact.$valid) {

            var reName = $scope.rname;
            var reTeam = $scope.tname;
            var reMail = $scope.email;
            var reMobile = $scope.cellNo;
            var reComment = $scope.comment;

            $http.post(BASE_HREF + 'contact/contactUs/' + reName + '/' + reTeam + '/' + reMail + '/' + reMobile + '/' + reComment + '').then(function (response) {
                if (response.data !== 0) {
                    var popup = document.getElementById("myPopup");
                    popup.classList.toggle("show");

                    setTimeout(function () { window.location.reload(); }, 3000);
                }
            }).catch(function (error) {
                $scope.errorMessage = error.data.message;
                var popup = document.getElementById("errorPopup");
                popup.classList.toggle("show");
            });

        } else {
            $scope.submitted = true;
        }

    };
});

testDataDashBoardModule.controller("SpecialRequestController", function ($scope, $http, UserService) {
    $http.defaults.headers.post["Content-Type"] = "application/json";

    initController();

    function initController() {
        // Request User data from backend
        $scope.pathurl =  window.location.pathname;
        UserService.getUser()
            .then(function (data) {
                $scope.rname = data.userName;
                $scope.email = data.mail;
            })
            .catch(function (error) {
                if (error.data.message) {
                    alert(error.data.message)
                    // window.location.reload();
                }
                console.error('Error', error.data.message)
            });
    };

    //######################## specialRequest #####################################

    $scope.checkemail = function (){

        var email = $scope.email;

        if(email == 'Karabo.Serope@absa.africa' || email == 'Mahlodi.Monyela@absa.africa' || email == 'Peter.James@absa.africa'){
            // alert('inside email');
            //  document.getElementById("ViewAllCaptured").style.visibility = "hidden";
        }else{
            //alert('outside email');
            document.getElementById("ViewAllCaptured").style.visibility = "hidden";
        }
    };


    $scope.specialReq = function () {

        if ($scope.specialrequest.$valid) {

            var reName = $scope.rname;
            var reTeam = $scope.tname;
            var reMail = $scope.email;
            var reMobile = $scope.cellNo;
            var reBureau = $scope.selectedBureau;
            var reComment = $scope.comment;

            $http.post(BASE_HREF + 'specialRequest/request/' + reName + '/' + reTeam + '/' + reMail + '/' + reMobile + '/' + reBureau + '/' + reComment + '').then(function (response) {

                if (response.data !== 0) {
                    var popup = document.getElementById("myPopup");
                    popup.classList.toggle("show");

                    setTimeout(function () { window.location.reload(); }, 3000);

                }
            }).catch(function (error) {
                $scope.errorMessage = error.data.message;
                var popup = document.getElementById("errorPopup");
                popup.classList.toggle("show");
            });

        } else {
            $scope.submitted = true;
        }

    };
});


testDataDashBoardModule.controller("SitSignFormViewController", function ($scope, $http, UserService) {
    $http.defaults.headers.post["Content-Type"] = "application/json";

    $("#warning-alert").hide();
    $("#danger-alert").hide();

    initController();

    function initController() {
        // Request User data from backend
        $scope.pathurl =  window.location.pathname;
        UserService.getUser()
            .then(function (data) {
                $scope.rname = data.fullName;
                $scope.email = data.mail;

            })
            .catch(function (error) {
                if (error.data && error.data.message) {
                    alert(error.data.message)
                    // window.location.reload();
                }
                console.error('Error', error)
            });
    };

    $scope.checkemail = function (){

        var email = $scope.email;

        if(email == 'Karabo.Serope@absa.africa' || email == 'Mahlodi.Monyela@absa.africa' || email == 'Peter.James@absa.africa'){
            // alert('inside email');
            //  document.getElementById("ViewAllCaptured").style.visibility = "hidden";
        }else{
            //alert('outside email');
            document.getElementById("ViewAllCaptured").style.visibility = "hidden";
        }
    };


    $scope.viewCapturedForms = function () {

        if ($scope.editsignoff.$valid) {
            var reEmail = $scope.email;
            var reInterfaceArea = $scope.interfaceArea;

				$http.get(BASE_HREF + 'SignOffViewForm/SignOffView/' + reEmail +'/' +reInterfaceArea +'').then(function (response) {
				  $scope.NtbTestData = response.data;

            }).catch(function (error) {
                $scope.errorMessage = error.data.message;
                var popup = document.getElementById("errorPopup");
                popup.classList.toggle("show");
                setTimeout(function () { window.location.reload(); }, 5000);
            });
        } else {
            $scope.submitted = true;
        }
    };


    $scope.viewCapturedFormsUsingEmail = function () {

       var reEmail = $scope.email;

            $http.get(BASE_HREF + 'SignOffViewForm/SignOffViewCaptureEmail/' + reEmail +'').then(function (response) {
                $scope.viewCaptured = response.data;

            }).catch(function (error) {
                $scope.errorMessage = error.data.message;
                var popup = document.getElementById("errorPopup");
                popup.classList.toggle("show");
                setTimeout(function () { window.location.reload(); }, 5000);
            });

    };

    //Create PDF from Table
    $scope.downloadPdfTable = function (){

            var sTable = document.getElementById('CapturedForms').innerHTML;

            var style = "<style>";
            style = style + "table {width: 100%;font: 17px Calibri;}";
            style = style + "table, th, td {border: solid 1px #DDD; border-collapse: collapse;";
            style = style + "padding: 2px 3px;text-align: center;}";
            style = style + "</style>";

            // CREATE A WINDOW OBJECT.
            var win = window.open('', '', 'height=700,width=700');

            win.document.write('<html><head>');
            win.document.write('<title>Profile</title>');   // <title> FOR PDF HEADER.
            win.document.write(style);          // ADD STYLE INSIDE THE HEAD TAG.
            win.document.write('</head>');
            win.document.write('<body>');
            win.document.write(sTable);         // THE TABLE CONTENTS INSIDE THE BODY TAG.
            win.document.write('</body></html>');

            win.document.close(); 	// CLOSE THE CURRENT WINDOW.

            win.print();    // PRINT THE CONTENTS.

    };

    $scope.createPDFTables = function(){
        var pdf = new jsPDF('p', 'pt', 'letter');
        // source can be HTML-formatted string, or a reference
        // to an actual DOM element from which the text will be scraped.
        source = $('#CapturedForms')[0];

        // we support special element handlers. Register them with jQuery-style
        // ID selector for either ID or node name. ("#iAmID", "div", "span" etc.)
        // There is no support for any other type of selectors
        // (class, of compound) at this time.
        specialElementHandlers = {
            // element with id of "bypass" - jQuery style selector
            '#bypassme': function (element, renderer) {
                // true = "handled elsewhere, bypass text extraction"
                return true
            }
        };
        margins = {
            top: 80,
            bottom: 60,
            left: 40,
            width: 522
        };
        // all coords and widths are in jsPDF instance's declared units
        // 'inches' in this case
        pdf.fromHTML(
            source, // HTML string or DOM elem ref.
            margins.left, // x coord
            margins.top, { // y coord
                'width': margins.width, // max width of content on PDF
                'elementHandlers': specialElementHandlers
            },

            function (dispose) {
                // dispose: object with X, Y of the last line add to the PDF
                //          this allow the insertion of new lines after html
                pdf.save('Test.pdf');
            }, margins);
    }
    ;
    $scope.viewAllCapturedForms = function () {

            var reEmail = $scope.email;
            var reInterfaceArea = $scope.interfaceArea;
            //Find All Captured Sign Off Forms
            $http.get(BASE_HREF + 'SignOffViewForm/SignOffViewAllForms/').then(function (response) {
                $scope.NtbTestData = response.data;

            }).catch(function (error) {
                $scope.errorMessage = error.data.message;
                var popup = document.getElementById("errorPopup");
                popup.classList.toggle("show");
                setTimeout(function () { window.location.reload(); }, 5000);
            });
    };
});


testDataDashBoardModule.controller("SitSignFormViewAllController", function ($scope, $http, UserService) {
    $http.defaults.headers.post["Content-Type"] = "application/json";

    $("#warning-alert").hide();
    $("#danger-alert").hide();

    initController();

    function initController() {
        // Request User data from backend
        $scope.pathurl =  window.location.pathname;
        UserService.getUser()
            .then(function (data) {
                $scope.rname = data.fullName;
                $scope.email = data.mail;

            })
            .catch(function (error) {
                if (error.data && error.data.message) {
                    alert(error.data.message)
                    // window.location.reload();
                }
                console.error('Error', error)
            });
    };

    $scope.checkemail = function (){

        var email = $scope.email;

        if(email == 'Karabo.Serope@absa.africa' || email == 'Mahlodi.Monyela@absa.africa' || email == 'Peter.James@absa.africa'){
            // alert('inside email');
            //  document.getElementById("ViewAllCaptured").style.visibility = "hidden";
        }else{
            //alert('outside email');
            document.getElementById("ViewAllCaptured").style.visibility = "hidden";
        }
    };


    //Get all Captured forms
    $http.get(BASE_HREF + 'SignOffViewForm/SignOffViewAllForms/').then(function (response) {
        $scope.SignOffForm = response.data;

    }).catch(function (error) {
        //$scope.errorMessage = error.data.message;
        var popup = document.getElementById("errorPopup");
        popup.classList.toggle("show");
        setTimeout(function () { window.location.reload(); }, 5000);
    });

    //Download Pfd file
    $scope.downloadPdf = function (b64) {

        if(b64 == 'NO ATTACHMENT' || b64 == 'undefined'){
            alert('Sign Off Form does not have an Attachment');

        }else{
          //  alert(b64);
            var base64Data = atob(b64);
            //alert(base64Data);

            var arrBuffer = base64ToArrayBuffer(base64Data);

            // It is necessary to create a new blob object with mime-type explicitly set
            // otherwise only Chrome works like it should
            var newBlob = new Blob([arrBuffer], { type: "application/pdf" });

            // IE doesn't allow using a blob object directly as link href
            // instead it is necessary to use msSaveOrOpenBlob
            if (window.navigator && window.navigator.msSaveOrOpenBlob) {
                window.navigator.msSaveOrOpenBlob(newBlob);
                return;
            }

            // For other browsers:
            // Create a link pointing to the ObjectURL containing the blob.
            var data = window.URL.createObjectURL(newBlob);

            var link = document.createElement('a');
            document.body.appendChild(link); //required in FF, optional for Chrome
            link.href = data;
            link.download = "SignOffForm.pdf";
            link.click();
            window.URL.revokeObjectURL(data);
            link.remove();
        }

        function base64ToArrayBuffer(data) {
            var binaryString = window.atob(data);
            var binaryLen = binaryString.length;
            var bytes = new Uint8Array(binaryLen);
            for (var i = 0; i < binaryLen; i++) {
                var ascii = binaryString.charCodeAt(i);
                bytes[i] = ascii;
            }
            return bytes;
        }
    };
});


testDataDashBoardModule.controller("SITSignOffController", function ($scope, $http, $timeout, UserService) {
    $http.defaults.headers.post["Content-Type"] = "application/json";

    $("#warning-alert").hide();
    $("#danger-alert").hide();

    initController();

    function initController() {
        // Request User data from backend
        $scope.pathurl =  window.location.pathname;
        UserService.getUser()
            .then(function (data) {
                $scope.rname = data.fullName;
                $scope.email = data.mail;

            })
            .catch(function (error) {
                if (error.data && error.data.message) {
                    alert(error.data.message)
                    // window.location.reload();
                }
                console.error('Error', error)
            });
    };


    $scope.checkemail = function (){

        var email = $scope.email;

        if(email == 'Karabo.Serope@absa.africa' || email == 'Mahlodi.Monyela@absa.africa' || email == 'Peter.James@absa.africa'){
            // alert('inside email');
            //  document.getElementById("ViewAllCaptured").style.visibility = "hidden";
        }else{
            //alert('outside email');
            document.getElementById("ViewAllCaptured").style.visibility = "hidden";
        }
    };


    $scope.choices = [
               ];

        $scope.showMessage = false;
        $scope.msg = "We can only Add Four(4) rows.";


           $scope.addNewChoice = function () {

               if($scope.choices.length <= 10){
                   $scope.choices.push({
                   });
               }else{
                   $scope.choices.length = $scope.choices.length-1;
                   if (messageTimer) {
                       $timeout.cancel(messageTimer);
                   }

                   $scope.showMessage = true;

                   messageTimer = $timeout(function () {
                       $scope.showMessage = false;
                   }, displayDuration);

               }
           };

             var messageTimer = false,
                displayDuration = 2000; // milliseconds (5000 ==> 5 seconds)

           $scope.famupdate = function (choices){

               if (messageTimer) {
                   $timeout.cancel(messageTimer);
               }

               $scope.showMessage = true;

               messageTimer = $timeout(function () {
                   $scope.showMessage = false;
               }, displayDuration);
           }

    $scope.saveSitForm = function () {

        //if ($scope.sitsignoff.$valid) {

            var reProjectChangeName = $scope.changeName;
            var reProjectChangeNumber = $scope.changeNumber;
            var reProjectChangeManager = $scope.projectManager;
            var reProjectChangeTower = $scope.changeTower;
            var reProjectChangeImplDate = $scope.implementationDate;
            var reProjectChangeCompleted = $scope.systemCompleted;
            var reProjectChangeCaptureEmail = $scope.email;
            var reDocument  = $scope.testfile;
            var base64String;

        if(reProjectChangeCompleted ==='No'){

            if (window.File && window.FileReader && window.FileList && window.Blob) {

                var f = document.getElementById('testfile').files[0]; // FileList object

                var reader = new FileReader();
                // Closure to capture the file information.
                reader.onload = (function(theFile) {
                    return function(e) {
                        var binaryData = e.target.result;
                        //Converting Binary Data to base 64
                        base64String = window.btoa(binaryData);

                     };
                })(f);
                // Read in the image file as a data URL.
                reader.readAsBinaryString(f);

            } else {
                alert('The File APIs are not fully supported in this browser.');
            }

        }else{
            reDocument = 'NO ATTACHMENT';
        }

        if(reProjectChangeNumber == undefined)
        {
                reProjectChangeNumber = "0";
        }

         $http.get(BASE_HREF + 'SitSignOffForm/SitSignOff/' + reProjectChangeName + '/' + reProjectChangeNumber + '/' + reProjectChangeManager + '/' + reProjectChangeTower + '/' + reProjectChangeImplDate + '/' + reProjectChangeCompleted +'/'+ reProjectChangeCaptureEmail + '/' + encodeURIComponent(JSON.stringify($scope.choices)) + '/' + reDocument+'').then(function (response) {

               $scope.buttonShow = true;
               $("#successModal").show();

              if (response.data !== 0) {

                  if(reProjectChangeCompleted ==='No'){

                      //alert(base64String);

                      var tokens  = response.data;

                      var encodedString = btoa(base64String);
                      //alert(encodedString);

                         $http.get(BASE_HREF + 'SitSignOffEditForm/UploadPDF/' + tokens + '/' + encodedString +'').then(function (response) {
                          $scope.buttonShow = true;

                        //  alert(response.data);
                          /*if (response.data !== 0) {
                              alert('File document Saved');
                          }else{
                              alert('File document not Saved');
                          }*/

                      }).catch(function (error) {
                          $scope.errorMessage = error.data.message;
                          var popup = document.getElementById("errorPopup");
                          popup.classList.toggle("show");
                          setTimeout(function () { window.location.reload(); }, 3000);
                      });

                  }
                    var popup = document.getElementById("myPopup");
                    popup.classList.toggle("show");

                    setTimeout(function () { window.location.reload(); }, 3000);
                }
            }).catch(function (error) {
                $scope.errorMessage = error.data.message;
                var popup = document.getElementById("errorPopup");
                popup.classList.toggle("show");
                setTimeout(function () { window.location.reload(); }, 3000);
            });
       /* } else {
            $scope.submitted = true;
        }*/
    };
});

testDataDashBoardModule.controller("SignOffFormAuthorizeController", function ($scope, $http, UserService) {
    $http.defaults.headers.post["Content-Type"] = "application/json";

    $("#warning-alert").hide();
    $("#danger-alert").hide();

    initController();

    function initController() {
        // Request User data from backend
        $scope.pathurl =  window.location.pathname;
        UserService.getUser()
            .then(function (data) {
                $scope.rname = data.fullName;
                $scope.email = data.mail;

            })
            .catch(function (error) {
                if (error.data && error.data.message) {
                    alert(error.data.message)
                    // window.location.reload();
                }
                console.error('Error', error)
            });
    };

    $scope.checkemail = function (){

        var email = $scope.email;

        if(email == 'Karabo.Serope@absa.africa' || email == 'Mahlodi.Monyela@absa.africa' || email == 'Peter.James@absa.africa'){
            // alert('inside email');
            //  document.getElementById("ViewAllCaptured").style.visibility = "hidden";
        }else{
            //alert('outside email');
            document.getElementById("ViewAllCaptured").style.visibility = "hidden";
        }
    };

    //Search for client to Approve Status
    $scope.searchForClientStatusUpdate = function () {
        if ($scope.signoffSIT.$valid) {
            var reEmail = $scope.email;
            var reInterfaceArea = $scope.interfaceArea;

             $http.get(BASE_HREF + 'Authorization/findClient/' + reEmail +'/' +reInterfaceArea +'').then(function (response) {
                $scope.NtbTestData = response.data;

            }).catch(function (error) {
               //  alert(error.data.message);
                $scope.errorMessage = error.data.message;
                var popup = document.getElementById("errorPopup");
                popup.classList.toggle("show");
                setTimeout(function () { window.location.reload(); }, 3000);
            });

        } else {
            $scope.submitted = true;
        }
    };

    //Update SIT Status
    $scope.UpdateSitFormStatus = function(testdata) {

        var reApproval = testdata.approval1;
        var resignOffID = testdata.sitsignoffid;


        if(reApproval == undefined){
            $scope.errorMessage = 'Please select Approval Or Decline.';
            var popup = document.getElementById("errorPopup1");
            popup.classList.toggle("show");
            setTimeout(function () { window.location.reload(); }, 3000);
        }else {

            $http.get(BASE_HREF + 'Authorization/approveStatus/' + reApproval + '/' + resignOffID + '').then(function (response) {
                $scope.NtbTestData = response.data;

                $scope.Action = "Add";

                if (response.data !== 0) {
                    $("#successModal").show();
                    $scope.open = function () {
                        $scope.showModal = true;
                    };
                    setTimeout(function () {
                        window.location.reload();
                    }, 5000);
                }
            }).catch(function (error) {
                $scope.errorMessage = error.data.message;
                var popup = document.getElementById("errorPopup");
                popup.classList.toggle("show");
                setTimeout(function () { window.location.reload(); }, 3000);
            });
        }
    };

});


testDataDashBoardModule.controller("EditSignOffController", function ($scope, $http, UserService) {
    $http.defaults.headers.post["Content-Type"] = "application/json";

    $("#warning-alert").hide();
    $("#danger-alert").hide();

    initController();

    function initController() {
        // Request User data from backend
        $scope.pathurl =  window.location.pathname;
        UserService.getUser()
            .then(function (data) {
                $scope.rname = data.fullName;
                $scope.email = data.mail;

            })
            .catch(function (error) {
                if (error.data && error.data.message) {
                    alert(error.data.message)
                    // window.location.reload();
                }
                console.error('Error', error)
            });
    };

    $scope.checkemail = function (){

        var email = $scope.email;

        if(email == 'Karabo.Serope@absa.africa' || email == 'Mahlodi.Monyela@absa.africa' || email == 'Peter.James@absa.africa'){
            // alert('inside email');
            //  document.getElementById("ViewAllCaptured").style.visibility = "hidden";
        }else{
            //alert('outside email');
            document.getElementById("ViewAllCaptured").style.visibility = "hidden";
        }
    };


    //Update Clients details
    $scope.UpdateEditSignOffDetails = function(testdata) {

        var reChangeName = testdata.changename;
        var reProjectManager = testdata.projectmanager;
        var reChangeNumber = testdata.changenumber;
        var reTower = testdata.tower;
        var reImplDate = testdata.implementationdate;
        var reSystemCompleted = testdata.testingcompleted;
        var reInterfaceArea = testdata.interfacearea;
        var reContactPerson = testdata.contactperson;
        var reContactEmail = testdata.contactemail;
        var reStatus = testdata.status;
        var resignOffID = testdata.sitsignoffid;

        $http.get(BASE_HREF + 'SitSignOffEditForm/SitSignOffEdit/' + reChangeName + '/' + reChangeNumber + '/' + reContactPerson + '/' + reImplDate  + '/' + reSystemCompleted+ '/' + reInterfaceArea + '/' + reProjectManager + '/' + reContactEmail + '/' + reTower + '/' + reStatus + '/'+ resignOffID + '').then(function (response) {
             $scope.Action = "Add";


                /*  ModalService.Open('successModal');*/
                if (response.data !== 0) {
                    $("#successModal").show();
                    $scope.open = function() {
                        $scope.showModal = true;
                    };
                    setTimeout(function () { window.location.reload(); }, 5000);
                }
            }).catch(function (error) {
                $scope.errorMessage = error.data.message;
                var popup = document.getElementById("errorPopup");
                popup.classList.toggle("show");
            setTimeout(function () { window.location.reload(); }, 3000);
            });
        };


    //Search for a client to edit
    $scope.searchEditSitSignOff = function () {

        if ($scope.signoff.$valid) {
            var reEmail = $scope.email;
            var reInterfaceArea = $scope.interfaceArea;

            $http.get(BASE_HREF + 'SitSignOffEditForm/findclients/' + reEmail +'/' +reInterfaceArea +'').then(function (response) {
                $scope.NtbTestData = response.data;

            }).catch(function (error) {
                $scope.errorMessage = error.data.message;
                var popup = document.getElementById("errorPopup");
                popup.classList.toggle("show");
                setTimeout(function () { window.location.reload(); }, 3000);
            });

        } else {
            $scope.submitted = true;
        }
    };
});
