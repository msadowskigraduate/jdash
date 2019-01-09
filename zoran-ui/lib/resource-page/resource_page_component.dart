import 'package:angular/angular.dart';
import 'package:angular_components/angular_components.dart';
import 'package:angular_router/angular_router.dart';
import 'package:zoran.io/401/401_page_component.dart';
import 'package:zoran.io/services/resource_service.dart';
import 'package:zoran.io/services/user_service.dart';
import 'package:zoran.io/services/zoran_service.dart';

@Component(
  selector: 'resources',
  templateUrl: 'resource_page_component.html',
  directives: const [
    coreDirectives,
    routerDirectives,
    MaterialIconComponent,
    MaterialButtonComponent,
    UnauthorizedComponent
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
  final NewResourceService _newResourceService;
  final _newPanel = new ResourceResponse(
      "",
      "Add new Resource",
      "", "", "",
      ResourceType.NEW,
      null, null,
      "Click to create new resource!",
      "", "","", "", null);

  List<ResourceResponse> moduleList = [];
  bool get auth => _userService.isAuthenticated();

  ResourcePageComponent(this.router, this._zoranService,
      this._userService, this._newResourceService);

  void navigate(String url) {
    router.navigate(url);
  }

  void createNewResource() {
    _newResourceService.createNewRequest();
    navigate('/new-resource');
  }

  @override
  Future ngOnInit() async {
    if (_userService.isAuthenticated()) {
      List<ResourceResponse> list = await _zoranService.getResources();
      moduleList.add(_newPanel);
      moduleList.addAll(list);
    }
  }

  String getStateForResource(ResourceResponse dto) {
    return dto.type.toString();
  }
}