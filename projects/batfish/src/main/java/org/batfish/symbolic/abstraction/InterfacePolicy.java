package org.batfish.symbolic.abstraction;

import java.util.List;
import java.util.Objects;
import java.util.SortedSet;
import javax.annotation.Nullable;
import org.batfish.datamodel.Prefix;
import org.batfish.datamodel.StaticRoute;
import org.batfish.symbolic.bdd.BDDAcl;
import org.batfish.symbolic.bdd.BDDRoute;

public class InterfacePolicy {

  private BDDAcl _acl;

  private BDDRoute _bgpPolicy;

  private Integer _ospfCost;

  private SortedSet<StaticRoute> _staticRoutes;

  private int _hcode = 0;

  // TODO: route reflectors etc

  public InterfacePolicy(
      @Nullable BDDAcl acl,
      @Nullable BDDRoute bgpPolicy,
      @Nullable Integer ospfCost,
      @Nullable SortedSet<StaticRoute> staticRoutes) {
    this._acl = acl;
    this._bgpPolicy = bgpPolicy;
    this._ospfCost = ospfCost;
    this._staticRoutes = staticRoutes;
  }

  public InterfacePolicy(InterfacePolicy other) {
    this._acl = other._acl;
    this._bgpPolicy = other._bgpPolicy;
    this._ospfCost = other._ospfCost;
    this._staticRoutes = other._staticRoutes;
  }

  public void free() {
    if (_acl != null) {
      _acl.free();
    }
    if (_bgpPolicy != null) {
      _bgpPolicy.free();
    }
  }

  public BDDAcl getAcl() {
    return _acl;
  }

  public BDDRoute getBgpPolicy() {
    return _bgpPolicy;
  }

  public Integer getOspfCost() {
    return _ospfCost;
  }

  public SortedSet<StaticRoute> getStaticRoutes() {
    return _staticRoutes;
  }

  @Override
  public int hashCode() {
    if (_hcode == 0) {
      int result = _ospfCost != null ? _ospfCost.hashCode() : 0;
      result = 31 * result + (_acl != null ? _acl.hashCode() : 0);
      result = 31 * result + (_bgpPolicy != null ? _bgpPolicy.hashCode() : 0);
      result = 31 * result + (_staticRoutes != null ? _staticRoutes.hashCode() : 0);
      _hcode = result;
    }
    return _hcode;
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof InterfacePolicy)) {
      return false;
    }
    InterfacePolicy other = (InterfacePolicy) o;
    return Objects.equals(_acl, other._acl)
        && Objects.equals(_bgpPolicy, other._bgpPolicy)
        && Objects.equals(_ospfCost, other._ospfCost)
        && Objects.equals(_staticRoutes, other._staticRoutes);
  }

  public InterfacePolicy restrict(Prefix pfx) {
    InterfacePolicy pol = new InterfacePolicy(this);
    if (pol._bgpPolicy != null) {
      pol._bgpPolicy = pol._bgpPolicy.restrict(pfx);
    }
    if (pol._acl != null) {
      pol._acl = pol._acl.restrict(pfx);
    }
    return pol;
  }

  public InterfacePolicy restrict(List<Prefix> prefixes) {
    InterfacePolicy pol = new InterfacePolicy(this);
    if (pol._bgpPolicy != null) {
      pol._bgpPolicy = pol._bgpPolicy.restrict(prefixes);
    }
    if (pol._acl != null) {
      pol._acl = pol._acl.restrict(prefixes);
    }
    return pol;
  }
}
