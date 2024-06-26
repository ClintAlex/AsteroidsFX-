package dk.sdu.mmmi.cbse.bulletsystem;

import dk.sdu.mmmi.cbse.common.bullet.Bullet;
import dk.sdu.mmmi.cbse.common.bullet.BulletSPI;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;

public class BulletControlSystem implements IEntityProcessingService, BulletSPI {
    @Override
    public void process(GameData gameData, World world) {

        for (Entity bullet : world.getEntities(Bullet.class)) {
            double changeX = Math.cos(Math.toRadians(bullet.getRotation()));
            double changeY = Math.sin(Math.toRadians(bullet.getRotation()));
            bullet.setX(bullet.getX() + changeX * 1.5);
            bullet.setY(bullet.getY() + changeY * 1.5);
            removeBulletIfOutOfBounds(bullet, gameData, world);
        }
    }

    @Override
    public Entity createBullet(Entity shooter, GameData gameData) {
        Entity bullet = new Bullet();
        ((Bullet) bullet).setOwner(shooter);
        bullet.setPolygonCoordinates(2, -2, 2, 2, -2, 2, -2, -2);
        double changeX = Math.cos(Math.toRadians(shooter.getRotation()));
        double changeY = Math.sin(Math.toRadians(shooter.getRotation()));
        bullet.setX(shooter.getX() + changeX * (10 + shooter.getRadius()));
        bullet.setY(shooter.getY() + changeY * (10 + shooter.getRadius()));
        bullet.setRotation(shooter.getRotation());
        bullet.setRadius(1);
        return bullet;
    }

    private void removeBulletIfOutOfBounds(Entity bullet, GameData gameData, World world) {
        if (bullet.getX() < 0 || bullet.getX() > gameData.getDisplayWidth() ||
                bullet.getY() < 0 || bullet.getY() > gameData.getDisplayHeight()) {
            world.removeEntity(bullet);
        }
    }
}