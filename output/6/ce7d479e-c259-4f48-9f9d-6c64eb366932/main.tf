resource "azurerm_resource_group" "appaccountserviceapi" {
  name     = "appaccountserviceapi-resources"
  location = "East US"
}

resource "azurerm_app_service_plan" "appaccountserviceapi" {
  name                = "appaccountserviceapi-appserviceplan"
  location            = azurerm_resource_group.appaccountserviceapi.location
  resource_group_name = azurerm_resource_group.appaccountserviceapi.name

  sku {
    tier = "Standard"
    size = "S1"
  }
}

resource "azurerm_app_service" "appaccountserviceapi" {
  name                = "appaccountserviceapi-app-service"
  location            = azurerm_resource_group.appaccountserviceapi.location
  resource_group_name = azurerm_resource_group.appaccountserviceapi.name
  app_service_plan_id = azurerm_app_service_plan.appaccountserviceapi.id

 /*
    # Settings for private Container Registires  
    DOCKER_REGISTRY_SERVER_URL      = ""
    DOCKER_REGISTRY_SERVER_USERNAME = ""
    DOCKER_REGISTRY_SERVER_PASSWORD = ""
    */
  }

  # Configure Docker Image to load on start
  site_config {
    linux_fx_version = "DOCKER|app-account-service-api:latest"
    always_on        = "true"
  }

  app_settings = {
    "SOME_KEY" = "some-value"
  }
}
