import 'dart:async';
import 'dart:html';

import 'package:angular/angular.dart';
import 'package:angular_components/angular_components.dart';
import 'package:angular_components/model/menu/menu.dart';
import 'package:angular_components/utils/disposer/disposer.dart';
import 'package:angular_router/angular_router.dart';
import 'package:observable/observable.dart';
import 'package:zoran.io/routing/routing.dart';
import 'package:zoran.io/tos/tos_component.dart';
import 'package:zoran.io/services/user_service.dart';
import 'package:zoran.io/services/zoran_service.dart';

@Component(
  selector: 'user',
  templateUrl: 'user_component.html',
  directives: const [
    coreDirectives,
    DropdownMenuComponent,
    MaterialIconComponent,
    MaterialMenuComponent,
    TermsOfServiceComponent,
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
  UserService _userService;
  final SelectionModel<String> colorSelection;
  final MenuModel<MenuItem> menuModel;
  final Router router;
  bool enabled;

  UserComponent._(this.menuModel, this.colorSelection, this.router, this._userService);

  @override
  Future ngOnInit() async {
    user = await _userService.getCurrentUser();
    enabled = user?.state == "ACTIVE";
  }

  bool isAuthorized() {
    return user != null && (user?.state == "ACTIVE" || user?.state == "INACTIVE");
  }

  @override
  void ngOnDestroy() {
    // TODO: implement ngOnDestroy
  }

  factory UserComponent(final Router router, final UserService userService) {
    var colorSelection = SelectionModel<String>.single();
    var menuModel = MenuModel<MenuItem>([
      MenuItemGroup<MenuItem>([
        MenuItem('About me...', action: () => router.navigate("/user"),
            itemSuffixes: ObservableList.from([
              IconAffix(
                  icon: IconWithAction(
                      'account_box', () => window.alert('action'), 'ariaLabel',
                      null, shouldCloseMenuOnTrigger: true)),
            ])),
        MenuItem('Community',
            action: () => window.alert('2'),
            itemSuffixes: ObservableList.from([
              IconAffix(
                  icon: IconWithAction(
                      'group', () => window.alert('action'), 'ariaLabel', null,
                      shouldCloseMenuOnTrigger: true)),
              IconAffix(
                  icon: IconWithAction('group_add', () => window.alert('action 1'),
                      'ariaLabel', null, shouldCloseMenuOnTrigger: true)),
            ])),
        MenuItem('Notifications',
            action: () => window.alert('3'),
            itemSuffixes: ObservableList.from([
              IconAffix(
                  icon: IconWithAction('notifications',
                          () => window.alert('action 1'), 'ariaLabel', null)),
            ])),
        MenuItem('Logout',
            action: () => window.alert('4'),
            itemSuffixes: ObservableList.from([
              IconAffix(
                  icon: IconWithAction('exit_to_app', () => window.alert('action 1'),
                      'ariaLabel', null)),
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

    return UserComponent._(menuModel, colorSelection, router, userService);
  }
}