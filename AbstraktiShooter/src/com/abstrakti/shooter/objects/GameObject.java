package com.abstrakti.shooter.objects;

import java.util.SortedMap;
import java.util.TreeMap;

import com.abstrakti.shooter.components.Component;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;

public abstract class GameObject {
	protected Body body;
	
	private SortedMap<Component.Type, Component> components = new TreeMap<Component.Type, Component>();
	
	public <C extends Component> boolean hasComponent(Component.Type ofType){
		return this.components.containsKey(ofType);
	}
	
	public <C extends Component> Component getComponent(Component.Type ofType){
		return this.components.get(ofType);
	}
	
	public void addComponent(Component component){
		this.components.put(component.TYPE, component);
	}
	
	public Body getBody() {
		return body;
	}
	
	
	/**
	 * Updates all components of this object.
	 */
	public void update(float deltaTime){
		for (Component cmp : this.components.values()) {
			cmp.update(deltaTime);
		}
	}
	
	public void draw(SpriteBatch batch){
		for (Component cmp : this.components.values()) {
			cmp.draw(batch);
		}
	}
}
