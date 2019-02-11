import 'package:angular/angular.dart';
import 'package:angular_components/angular_components.dart';
import 'package:angular_router/angular_router.dart';

@Component(
  selector: 'welcome-page',
  templateUrl: 'welcome_page_component.html',
  directives: const [
    coreDirectives,
    routerDirectives,
    MaterialIconComponent,
    MaterialButtonComponent
  ],
  styleUrls: const ['welcome_page_component.scss.css'],
  providers: const [
    materialProviders,
  ],
)
class WelcomePageComponent {
  final Router router;

  WelcomePageComponent(this.router);

  static List<ModuleDto> moduleList = [
    new ModuleDto("Resources", "developer_mode",
        "Add new Resources or Edit existing ones.", "RESOURCES", "resources"),
    new ModuleDto("User Profile", "perm_identity",
        "View and edit your user details.", "ACCOUNT", "user"),
    new ModuleDto("Browse", "search",
        "Search facility allows to browse shared resources.", "BROWSE", "resource_browser_component"),
    new ModuleDto("Management", "settings",
        "Manage your intergations, deployments and such..", "MANAGE RESOURCES", "management"),
    new ModuleDto("Place for additional card/module", "ADD_NEW", null, null, ""),
  ];

  void navigate(String url) {
    router.navigate(url);
  }
}

class ModuleDto {
  String name;
  String icon;
  String text;
  String buttonText;
  String uri;

  ModuleDto(this.name, this.icon, this.text, this.buttonText, this.uri);
}