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
    new ModuleDto("URI Checker", "u56.png",
        "The goal of URI checker is to check if the URI that GDSMs created is"
            " unique.", "CHECK URI", "uri-checker"),
    new ModuleDto("Named query", "u56.png",
        "Named Query enable GDSMs to query across the GDSR with full "
            "flexibility", "NAMED QUERY", ""),
    new ModuleDto("Search", "u56.png",
        "Search facility allows to search through GDSR or RTS.", "SEARCH", ""),
    new ModuleDto("Metadata Management", "u56.png",
        "Metadata Management allows the GDSMs to interact with the graphs in a transparent manner.",
        "MANAGE METADATA", ""),
    new ModuleDto("Governance", "u56.png",
        "Information of the terms from the different models that they need to "
            "promote to the status of Ready to Publish", "MANAGE TERMS", ""),
    new ModuleDto("Place for additionalcard/module", "u56.png", null, null, ""),
  ];

  void navigate(String url) {
    router.navigate(url);
  }
}

class ModuleDto {
  String name;
  String image;
  String text;
  String buttonText;
  String uri;

  ModuleDto(this.name, this.image, this.text, this.buttonText, this.uri);
}