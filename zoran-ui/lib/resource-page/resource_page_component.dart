
import 'package:angular/angular.dart';
import 'package:angular_components/angular_components.dart';
import 'package:angular_router/angular_router.dart';
import 'package:sheets_dashboard/user/user_service.dart';
import 'package:sheets_dashboard/zoran_service.dart';

@Component(
  selector: 'resources',
  templateUrl: 'resource_page_component.html',
  directives: const [
    coreDirectives,
    routerDirectives,
    MaterialIconComponent,
    MaterialButtonComponent
  ],
  styleUrls: const ['resource_page_component.scss.css'],
  providers: const [
    materialProviders,
    const ClassProvider(ZoranService),
    const ClassProvider(UserService),
  ],
)
class ResourcePageComponent implements OnInit {
  final Router router;
  final UserService _userService;
  final ZoranService _zoranService;
  bool authorized = false;
  ResourcePageComponent(this.router, this._zoranService, this._userService);

  List<ResourceModuleDto> moduleList = [
    new ResourceModuleDto("Add new Resource", "", "", "NEW", "Click to create"
        " new resource!",""),
  ];

  void navigate(String url) {
    router.navigate(url);
  }

  void getUserState() async {
    UserDto userDto = await _userService.isAuthenticated();
    authorized = userDto != null && userDto.state == "ACTIVE";
  }

  bool isAuthorized() {
    if(!authorized) {
      getUserState();
    }
    return authorized;
  }

  @override
  void ngOnInit() {
    List<ResourceModuleDto> list = _zoranService.getResources();
    moduleList.addAll(list);
  }

  String getStateForResource(ResourceModuleDto dto) {
    return dto.type;
  }
}


class ResourceModuleDto {
  String projectName;
  String resourceVisibility;
  String author;
  String type;
  String description;
  String resourceIdentifier;

  ResourceModuleDto(this.projectName, this.resourceVisibility, this.author,
      this.type, this.description, this.resourceIdentifier);
}