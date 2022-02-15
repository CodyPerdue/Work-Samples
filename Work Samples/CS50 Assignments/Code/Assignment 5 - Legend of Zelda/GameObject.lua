--[[
    GD50
    Legend of Zelda

    Author: Colton Ogden
    cogden@cs50.harvard.edu
]]

GameObject = Class{}

function GameObject:init(def, x, y)
    -- string identifying this object type
    self.type = def.type

    self.texture = def.texture
    self.frame = def.frame or 1

    -- whether it acts as an obstacle/consumable or not
    self.solid = def.solid
	self.consumable = def.consumable

    self.defaultState = def.defaultState
    self.state = self.defaultState
    self.states = def.states

    -- dimensions
    self.x = x
    self.y = y
    self.width = def.width
    self.height = def.height
	self.origWidth = def.width
	self.origHeight = def.width

	-- projectile variables
	self.fired = false
	self.movementSpeed = def.movementSpeed or 0
	self.distanceTraveled = 0
	self.dx = 0
	self.dy = 0

	-- methods
    self.onCollide = def.onCollide
	self.onConsume = def.onConsume
end

function GameObject:fire(entity)
	self.fired = true

	if entity.direction == 'left' then
		self.x = entity.x - self.origWidth
		self.y = entity.y + (entity.height / 2) - (self.origHeight / 2)
		self.dx = -self.movementSpeed
	elseif entity.direction == 'right' then
		self.x = entity.x + entity.width
		self.y = entity.y + (entity.height / 2) - (self.origHeight / 2)
		self.dx = self.movementSpeed
	elseif entity.direction == 'up' then
		self.x = entity.x + (entity.width / 2) - (self.origWidth / 2)
		self.y = entity.y - self.origHeight
		self.dy = -self.movementSpeed
	else -- entity.direction == 'down'
		self.x = entity.x + (entity.width / 2) - (self.origWidth / 2)
		self.y = entity.y + self.origHeight
		self.dy = self.movementSpeed
	end

	self.width = self.origWidth
	self.height = self.origHeight
end

function GameObject:collides(target)
    local selfY, selfHeight = self.y + self.height / 2, self.height - self.height / 2

    return not (self.x + self.width < target.x or self.x > target.x + target.width or
                selfY + selfHeight < target.y or selfY > target.y + target.height)
end

function GameObject:update(dt)
	self.x = self.x + self.dx * dt
	self.y = self.y + self.dy * dt
	self.distanceTraveled = self.distanceTraveled + (self.dx * dt) + (self.dy * dt)
end

function GameObject:render(adjacentOffsetX, adjacentOffsetY)
    love.graphics.draw(gTextures[self.texture], gFrames[self.texture][self.states[self.state].frame or self.frame],
        self.x + adjacentOffsetX, self.y + adjacentOffsetY)

	-- debugging
	-- love.graphics.setColor(255, 0, 255, 255)
    -- love.graphics.rectangle('line', self.x, self.y, self.width, self.height)
    -- love.graphics.setColor(255, 255, 255, 255)
end
