/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package test.domain.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

import test.domain.Address;
import test.domain.DomainPackage;
import test.domain.Group;
import test.domain.User;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>User</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link test.domain.impl.UserImpl#getName <em>Name</em>}</li>
 *   <li>{@link test.domain.impl.UserImpl#getPass <em>Pass</em>}</li>
 *   <li>{@link test.domain.impl.UserImpl#getGroups <em>Groups</em>}</li>
 *   <li>{@link test.domain.impl.UserImpl#getAddresses <em>Addresses</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class UserImpl extends EntityImpl implements User {
	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;
	/**
	 * The default value of the '{@link #getPass() <em>Pass</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPass()
	 * @generated
	 * @ordered
	 */
	protected static final String PASS_EDEFAULT = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected UserImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return DomainPackage.Literals.USER;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return (String)eDynamicGet(DomainPackage.USER__NAME, DomainPackage.Literals.USER__NAME, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		eDynamicSet(DomainPackage.USER__NAME, DomainPackage.Literals.USER__NAME, newName);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getPass() {
		return (String)eDynamicGet(DomainPackage.USER__PASS, DomainPackage.Literals.USER__PASS, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPass(String newPass) {
		eDynamicSet(DomainPackage.USER__PASS, DomainPackage.Literals.USER__PASS, newPass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<Group> getGroups() {
		return (EList<Group>)eDynamicGet(DomainPackage.USER__GROUPS, DomainPackage.Literals.USER__GROUPS, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<Address> getAddresses() {
		return (EList<Address>)eDynamicGet(DomainPackage.USER__ADDRESSES, DomainPackage.Literals.USER__ADDRESSES, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case DomainPackage.USER__ADDRESSES:
				return ((InternalEList<?>)getAddresses()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case DomainPackage.USER__NAME:
				return getName();
			case DomainPackage.USER__PASS:
				return getPass();
			case DomainPackage.USER__GROUPS:
				return getGroups();
			case DomainPackage.USER__ADDRESSES:
				return getAddresses();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case DomainPackage.USER__NAME:
				setName((String)newValue);
				return;
			case DomainPackage.USER__PASS:
				setPass((String)newValue);
				return;
			case DomainPackage.USER__GROUPS:
				getGroups().clear();
				getGroups().addAll((Collection<? extends Group>)newValue);
				return;
			case DomainPackage.USER__ADDRESSES:
				getAddresses().clear();
				getAddresses().addAll((Collection<? extends Address>)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case DomainPackage.USER__NAME:
				setName(NAME_EDEFAULT);
				return;
			case DomainPackage.USER__PASS:
				setPass(PASS_EDEFAULT);
				return;
			case DomainPackage.USER__GROUPS:
				getGroups().clear();
				return;
			case DomainPackage.USER__ADDRESSES:
				getAddresses().clear();
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case DomainPackage.USER__NAME:
				return NAME_EDEFAULT == null ? getName() != null : !NAME_EDEFAULT.equals(getName());
			case DomainPackage.USER__PASS:
				return PASS_EDEFAULT == null ? getPass() != null : !PASS_EDEFAULT.equals(getPass());
			case DomainPackage.USER__GROUPS:
				return !getGroups().isEmpty();
			case DomainPackage.USER__ADDRESSES:
				return !getAddresses().isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //UserImpl
