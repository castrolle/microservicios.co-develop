provider "azurerm" {
  tenant_id       = "XXXXXXXXX-XXXX-XXXX-XXXX-XXXXXXXXX"
  subscription_id = "XXXXXXXXX-XXXX-XXXX-XXXX-XXXXXXXXX"
  client_id       = "XXXXXXXXX-XXXX-XXXX-XXXX-XXXXXXXXX"
  client_secret   = "j-XXXXXXX~XXXXXXX_Ip_XXXXXXX"
  features {}
}

# Create the resource group
resource "azurerm_resource_group" "rg" {
  name     = "rg-companydevelop"
  location = "eastus"
}

# Create the Linux App Service Plan
resource "azurerm_service_plan" "appserviceplan" {
  name                = "webapp-asp-app-account-service-api"
  location            = azurerm_resource_group.rg.location
  resource_group_name = azurerm_resource_group.rg.name
  os_type             = "Linux"
  sku_name            = "B1"
}


locals {
 env_variables = {
   APP_VERSION = "develop"
   DOCKER_REGISTRY_SERVER_URL            = "https://companydevelop.azurecr.io"
   DOCKER_REGISTRY_SERVER_USERNAME       = "companydevelop"
   DOCKER_REGISTRY_SERVER_PASSWORD       = "JeH7ss8njizxHz9d4ReFUuZCMszcq+Vjz+Pj4BCXNw+ACRCZ/Y6y"
   DOCKER_CUSTOM_IMAGE_NAME              = "companydevelop.azurecr.io/app-account-service-api:20230131.1-main"
   WEBSITES_ENABLE_APP_SERVICE_STORAGE = "false"
    SERVICE_TEST_SERVICE_URL  = "http://localhost"
    SERVICE_TEST_SERVICE_CLIENT_ID = "57aYr56G2CcQSgzG"
    SERVICE_TEST_SERVICE_CLIENT_SECRET = "1SmRBGuRh"
    SERVICE_TEST_SERVICE_CUSTOMER = "/customer"
 }
}

# Create the web app, pass in the App Service Plan ID
resource "azurerm_linux_web_app" "webapp" {
  name                  = "webapp-app-account-service-api"
  location              = azurerm_resource_group.rg.location
  resource_group_name   = azurerm_resource_group.rg.name
  service_plan_id       = azurerm_service_plan.appserviceplan.id
  https_only            = true
   site_config {
    application_stack {
      docker_image     = "app-account-service-api"
      docker_image_tag = "20230131.1-main"
    }
  }

 app_settings = local.env_variables
}

resource "azurerm_container_registry" "registry" {
  name                = "companydevelop"
  resource_group_name = azurerm_resource_group.rg.name
  location            = azurerm_resource_group.rg.location
  sku                 = "Standard"
  admin_enabled       = true
  
}
