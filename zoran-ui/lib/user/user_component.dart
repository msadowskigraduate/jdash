import 'dart:async';
import 'dart:html';

import 'package:angular/angular.dart';
import 'package:angular_components/angular_components.dart';
import 'package:angular_components/model/menu/menu.dart';
import 'package:angular_components/utils/disposer/disposer.dart';
import 'package:angular_router/angular_router.dart';
import 'package:observable/observable.dart';
import 'package:sheets_dashboard/routing/routing.dart';
import 'package:sheets_dashboard/zoran_service.dart';

@Component(
  selector: 'user',
  templateUrl: 'user_component.html',
  directives: const [
    coreDirectives,
    DropdownMenuComponent,
    MaterialIconComponent,
    MaterialMenuComponent,
  ],
  providers: const [
    materialProviders,
    popupBindings,
    const ClassProvider(ZoranService),
    const ClassProvider(AppRoutes)],
)
class UserComponent implements OnDestroy, OnInit {
  String iconColor = "white";
  UserDto user;
  final ZoranService _zoranService;
  final SelectionModel<String> colorSelection;
  final MenuModel<MenuItem> menuModel;
  final Router router;
  final Disposer _disposer;

  UserComponent._(this.menuModel, this.colorSelection, this._disposer, this.router, this._zoranService);

  @override
  Future ngOnInit() async {
    user = await _zoranService.isAuthenticated();
  }

  bool isAuthorized() {
    return user != null && user?.state == "ACTIVE";
  }

  @override
  void ngOnDestroy() {
    // TODO: implement ngOnDestroy
  }

  factory UserComponent(final Router router, final ZoranService zoranService) {
    var colorSelection = SelectionModel<String>.single();
    var menuModel = MenuModel<MenuItem>([
      MenuItemGroup<MenuItem>([
        MenuItem('red', action: () => router.navigate("/user")),
        MenuItem('With an icon suffix',
            action: () => window.alert('2'),
            itemSuffixes: ObservableList.from([
              IconAffix(
                  icon: IconWithAction(
                      'delete', () => window.alert('action'), 'ariaLabel', null,
                      shouldCloseMenuOnTrigger: true))
            ])),
        MenuItem('With text suffix',
            action: () => window.alert('3'),
            itemSuffixes:
            ObservableList.from([CaptionAffix(text: 'Ctrl + V')])),
        MenuItem('With multiple suffixes',
            action: () => window.alert('4'),
            itemSuffixes: ObservableList.from([
              IconAffix(
                  icon: IconWithAction('delete', () => window.alert('action 1'),
                      'ariaLabel', null)),
              IconAffix(icon: Icon('accessible')),
              CaptionAffix(text: 'some text'),
              IconAffix(icon: Icon('autorenew')),
            ]))
      ])
    ]);
    final disposer = Disposer.oneShot();
    final typeSelection = SelectionModel<String>.multi();
    final toolSelection = SelectionModel<String>.multi();
    final planeSelection = SelectionModel<String>.multi();
    
    disposer.addStreamSubscription(typeSelection.changes.listen((_) {
      if (typeSelection.isNotEmpty) {
        toolSelection.select('Chisels');
      } else {
        toolSelection.deselect('Chisels');
      }
    }));

    disposer.addStreamSubscription(planeSelection.changes.listen((_) {
      if (planeSelection.isNotEmpty) {
        toolSelection.select('Planes');
      } else {
        toolSelection.deselect('Planes');
      }
    }));

    return UserComponent._(menuModel, colorSelection, disposer, router, zoranService);
  }
}