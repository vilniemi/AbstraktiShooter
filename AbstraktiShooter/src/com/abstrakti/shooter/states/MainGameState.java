package com.abstrakti.shooter.states;

import com.abstrakti.shooter.io.GameScreen;
import com.abstrakti.shooter.managers.AssetManager;
import com.abstrakti.shooter.managers.StateManager;
import com.abstrakti.shooter.map.Level;
import com.abstrakti.shooter.map.Tile;
import com.abstrakti.shooter.objects.Bullet;
import com.abstrakti.shooter.objects.Player;
import com.abstrakti.shooter.objects.Wall;
import com.abstrakti.shooter.triggers.Trigger;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;

class MainGameState extends State {
	private GameScreen gameScreen;
	private Level currentLevel;
	private Sound bulletFleshSound;
	private Sound bulletWallSound;
	

	public MainGameState(StateManager manager, SpriteBatch batch) {
		super(manager);	
		this.bulletFleshSound = AssetManager.getInstance().getbulletFleshSound();
		this.bulletWallSound = AssetManager.getInstance().getbulletWallSound();
	}

	@Override
	public void create() {
		
		this.currentLevel = new Level("level1.tmx");
		this.currentLevel.init();
		GameScreen.getInstance().setLevel(currentLevel);
		createCollisionListener();
	}
	
	private void createCollisionListener() {
		World world = currentLevel.getWorld();

		world.setContactListener(new ContactListener() {
			public void beginContact(Contact contact) {
				Fixture fixtureA = contact.getFixtureA();
				Fixture fixtureB = contact.getFixtureB();

				checkCollisionBulletAndWall(fixtureA, fixtureB);
				checkCollisionBulletAndPlayer(fixtureA, fixtureB);
				checkCollisionPlayerTrigger(fixtureA, fixtureB);
			}

			private void checkCollisionBulletAndWall(Fixture fixtureA, Fixture fixtureB) {
				if ((fixtureA.getBody().getUserData() instanceof Wall) && (fixtureB.getBody().getUserData() instanceof Bullet)) {
					System.out.println("wall and bullet ");
					Bullet b = (Bullet)fixtureB.getBody().getUserData();
					b.hurt(1);
					//bulletWallSound.play(1.0f);
				}
				/*
                 if ((fixtureA.getBody().getUserData() instanceof Bullet) && (fixtureB.getBody().getUserData() instanceof Wall)) {
                 	System.out.println("bullet and wall");
                 }  */
			}
			public void checkCollisionPlayerTrigger(Fixture fixtureA, Fixture fixtureB) {
				if ((fixtureA.getBody().getUserData() instanceof Trigger && (fixtureB.getBody().getUserData() instanceof Player))){
					Trigger trigger = (Trigger)fixtureA.getBody().getUserData();
					Player player = (Player)fixtureB.getBody().getUserData();
					trigger.execute(player);
				}
			}

			public void checkCollisionBulletAndPlayer(Fixture fixtureA, Fixture fixtureB) {  	
				Object sound;
				if (((fixtureA.getBody().getUserData() instanceof Bullet) && (fixtureB.getBody().getUserData() instanceof Player))) {
					System.out.println("bullet and player");
					Bullet b = (Bullet)fixtureA.getBody().getUserData();
					b.hurt(1);
					Player p = (Player)fixtureB.getBody().getUserData();
					p.hurt(1);
					bulletFleshSound.play(1.0f);
				}
				if (((fixtureA.getBody().getUserData() instanceof Player) && (fixtureB.getBody().getUserData() instanceof Bullet))) {
					System.out.println("player and bullet");
					Bullet b = (Bullet)fixtureB.getBody().getUserData();
					b.hurt(1);
					Player p = (Player)fixtureA.getBody().getUserData();
					p.hurt(1);
					bulletFleshSound.play(1.0f);
				}
			}

            public void endContact(Contact contact) {
                Fixture fixtureA = contact.getFixtureA();
                Fixture fixtureB = contact.getFixtureB();
               // Gdx.app.log("endContact", "between " + fixtureA.toString() + " and " + fixtureB.toString());
            }

			@Override
			public void preSolve(Contact contact, Manifold oldManifold) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void postSolve(Contact contact, ContactImpulse impulse) {
				// TODO Auto-generated method stub
				
			}

        });
	}
	
	private void detectContacts() {
		World world = currentLevel.getWorld();
		   int numContacts = world.getContactCount();
	        if (numContacts > 0) {
	            Gdx.app.log("contact", "start of contact list");
	            for (Contact contact : world.getContactList()) {
	                Fixture fixtureA = contact.getFixtureA();
	                Fixture fixtureB = contact.getFixtureB();
	                Gdx.app.log("contact", "between " + fixtureA.toString() + " and " + fixtureB.toString());
	            }
	            Gdx.app.log("contact", "end of contact list");
	        }
	}

	private void wallContact() {
		
		
	}
	
	private void playerContact() {
		
	}
	@Override
	public void dispose() {
		this.gameScreen.dispose();
	}

	@Override
	public void render() {				
		this.update();
		GameScreen.getInstance().render(Gdx.graphics.getDeltaTime());
	}

	private void update() {
		this.currentLevel.update(Gdx.graphics.getDeltaTime());
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}
	
	
}
