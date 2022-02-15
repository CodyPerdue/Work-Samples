--[[
    GD50
    Breakout Remake

    -- Paddle Class --

    Author: Cody Perdue
    codyperdue88@hotmail.com

    Represents a powerup that will spawn on certain conditions.
	Powerup can either spawn more balls, or will spawn a key
	used to unlock locked bricks.
]]

Powerup = Class{}

function Powerup:init(skin)
    -- simple positional and dimensional variables
    self.width = 16
    self.height = 16

    -- this variable keeps track of movement on the Y axis,
	-- constant movement from top of screen to bottom
    self.dy = POWERUP_SPEED
	
	-- these variables will keep track of the X and Y position of the powerup
	self.x = math.random(0, VIRTUAL_WIDTH - self.width)
	self.y = 0 - self.height

    -- this will effectively be the type of powerup, and we will index
    -- our table of Quads relating to the global block texture using this
    self.skin = skin
	
	-- this variable will set the powerup in motion once ready
	self.moving = false
end

function Powerup:collides(target)
    -- first, check to see if the left edge of either is farther to the right
    -- than the right edge of the other
    if self.x > target.x + target.width or target.x > self.x + self.width then
        return false
    end

    -- then check to see if the bottom edge of either is higher than the top
    -- edge of the other
    if self.y > target.y + target.height or target.y > self.y + self.height then
        return false
    end 

    -- if the above aren't true, they're overlapping
    return true
end

function Powerup:update(dt)
	if self.moving then
		self.y = self.y + self.dy * dt
	end
end

function Powerup:render()
    love.graphics.draw(gTextures['main'], gFrames['powerups'][self.skin], self.x, self.y)
end