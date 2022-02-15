--[[
    GD50
    Breakout Remake

    -- PlayState Class --

    Author: Colton Ogden
    cogden@cs50.harvard.edu

    Represents the state of the game in which we are actively playing;
    player should control the paddle, with the ball actively bouncing between
    the bricks, walls, and the paddle. If the ball goes below the paddle, then
    the player should lose one point of health and be taken either to the Game
    Over screen if at 0 health or the Serve screen otherwise.
]]

PlayState = Class{__includes = BaseState}

--[[
    We initialize what's in our PlayState via a state table that we pass between
    states as we go from playing to serving.
]]
function PlayState:enter(params)
    self.paddle = params.paddle
    self.bricks = params.bricks
    self.health = params.health
    self.score = params.score
    self.highScores = params.highScores
    self.ball = params.ball
    self.level = params.level
	self.recoverPoints = params.recoverPoints

    -- give ball random starting velocity
    self.ball.dx = math.random(-200, 200)
    self.ball.dy = math.random(-50, -60)
	
	-- initialize powerup and timers for powerup spawning
	self.multiball = Powerup(9)
	self.key = Powerup(10)
	self.keyObtained = false
	self.timer = 0
	self.multiballTime = math.random(5, 10)
	self.keyTime = math.random(30, 40)
	
	-- initialize extra balls spawned from multiball powerup
	self.ball2 = Ball()
	self.ball2.skin = self.ball.skin
	self.ball2.x = VIRTUAL_WIDTH / 2
	self.ball2.y = VIRTUAL_HEIGHT
	
	self.ball3 = Ball()
	self.ball3.skin = self.ball.skin
	self.ball3.x = VIRTUAL_WIDTH / 2
	self.ball3.y = VIRTUAL_HEIGHT
	
	-- initiialize score requirement for paddle-size increasing
	self.paddleScore = 0
	self.paddleThreshold = 5000
end

function PlayState:update(dt)
    if self.paused then
        if love.keyboard.wasPressed('space') then
            self.paused = false
            gSounds['pause']:play()
        else
            return
        end
    elseif love.keyboard.wasPressed('space') then
        self.paused = true
        gSounds['pause']:play()
        return
    end

    -- update positions based on velocity
    self.paddle:update(dt)
    self.ball:update(dt)
	self.ball2:update(dt)
	self.ball3:update(dt)

    if self.ball:collides(self.paddle) then
        -- raise ball above paddle in case it goes below it, then reverse dy
        self.ball.y = self.paddle.y - 8
        self.ball.dy = -self.ball.dy

        --
        -- tweak angle of bounce based on where it hits the paddle
        --

        -- if we hit the paddle on its left side while moving left...
        if self.ball.x < self.paddle.x + (self.paddle.width / 2) and self.paddle.dx < 0 then
            self.ball.dx = -50 + -(8 * (self.paddle.x + self.paddle.width / 2 - self.ball.x))
        
        -- else if we hit the paddle on its right side while moving right...
        elseif self.ball.x > self.paddle.x + (self.paddle.width / 2) and self.paddle.dx > 0 then
            self.ball.dx = 50 + (8 * math.abs(self.paddle.x + self.paddle.width / 2 - self.ball.x))
        end

        gSounds['paddle-hit']:play()
    end
	
	if self.ball2:collides(self.paddle) then
        -- raise ball above paddle in case it goes below it, then reverse dy
        self.ball2.y = self.paddle.y - 8
        self.ball2.dy = -self.ball2.dy

        --
        -- tweak angle of bounce based on where it hits the paddle
        --

        -- if we hit the paddle on its left side while moving left...
        if self.ball2.x < self.paddle.x + (self.paddle.width / 2) and self.paddle.dx < 0 then
            self.ball2.dx = -50 + -(8 * (self.paddle.x + self.paddle.width / 2 - self.ball2.x))
        
        -- else if we hit the paddle on its right side while moving right...
        elseif self.ball2.x > self.paddle.x + (self.paddle.width / 2) and self.paddle.dx > 0 then
            self.ball2.dx = 50 + (8 * math.abs(self.paddle.x + self.paddle.width / 2 - self.ball2.x))
        end

        gSounds['paddle-hit']:play()
    end
	
	if self.ball3:collides(self.paddle) then
        -- raise ball above paddle in case it goes below it, then reverse dy
        self.ball3.y = self.paddle.y - 8
        self.ball3.dy = -self.ball3.dy

        --
        -- tweak angle of bounce based on where it hits the paddle
        --

        -- if we hit the paddle on its left side while moving left...
        if self.ball3.x < self.paddle.x + (self.paddle.width / 2) and self.paddle.dx < 0 then
            self.ball3.dx = -50 + -(8 * (self.paddle.x + self.paddle.width / 2 - self.ball3.x))
        
        -- else if we hit the paddle on its right side while moving right...
        elseif self.ball3.x > self.paddle.x + (self.paddle.width / 2) and self.paddle.dx > 0 then
            self.ball3.dx = 50 + (8 * math.abs(self.paddle.x + self.paddle.width / 2 - self.ball3.x))
        end

        gSounds['paddle-hit']:play()
    end

    -- detect collision across all bricks with the ball
    for k, brick in pairs(self.bricks) do

        -- only check collision if we're in play
        if brick.inPlay and self.ball:collides(brick) then
		
			-- set the brick to unlocked if key powerup obtained
			if self.keyObtained == true then
				brick.unlocked = true
			end

            -- add to score (unless locked), bonus points if unlocked brick destroyed
			if not (brick.locked == true and brick.unlocked == false) then
				if brick.locked == true and brick.unlocked == true then
					self.score = self.score + 2000
					self.paddleScore = self.paddleScore + 2000
				else
					self.score = self.score + (brick.tier * 200 + brick.color * 25)
					self.paddleScore = self.paddleScore + (brick.tier * 200 + brick.color * 25)
				end
			end

            -- trigger the brick's hit function, which removes it from play
            brick:hit()

            -- if we have enough points, recover a point of health
            if self.score > self.recoverPoints then
                -- can't go above 3 health
                self.health = math.min(3, self.health + 1)

                -- multiply recover points by 2
                self.recoverPoints = math.min(100000, self.recoverPoints * 2)

                -- play recover sound effect
                gSounds['recover']:play()
            end

            -- go to our victory screen if there are no more bricks left
            if self:checkVictory() then
                gSounds['victory']:play()

                gStateMachine:change('victory', {
                    level = self.level,
                    paddle = self.paddle,
                    health = self.health,
                    score = self.score,
                    highScores = self.highScores,
                    ball = self.ball,
                    recoverPoints = self.recoverPoints
                })
            end

            --
            -- collision code for bricks
            --
            -- we check to see if the opposite side of our velocity is outside of the brick;
            -- if it is, we trigger a collision on that side. else we're within the X + width of
            -- the brick and should check to see if the top or bottom edge is outside of the brick,
            -- colliding on the top or bottom accordingly 
            --

            -- left edge; only check if we're moving right, and offset the check by a couple of pixels
            -- so that flush corner hits register as Y flips, not X flips
            if self.ball.x + 2 < brick.x and self.ball.dx > 0 then
                
                -- flip x velocity and reset position outside of brick
                self.ball.dx = -self.ball.dx
                self.ball.x = brick.x - 8
            
            -- right edge; only check if we're moving left, , and offset the check by a couple of pixels
            -- so that flush corner hits register as Y flips, not X flips
            elseif self.ball.x + 6 > brick.x + brick.width and self.ball.dx < 0 then
                
                -- flip x velocity and reset position outside of brick
                self.ball.dx = -self.ball.dx
                self.ball.x = brick.x + 32
            
            -- top edge if no X collisions, always check
            elseif self.ball.y < brick.y then
                
                -- flip y velocity and reset position outside of brick
                self.ball.dy = -self.ball.dy
                self.ball.y = brick.y - 8
            
            -- bottom edge if no X collisions or top collision, last possibility
            else
                
                -- flip y velocity and reset position outside of brick
                self.ball.dy = -self.ball.dy
                self.ball.y = brick.y + 16
            end

            -- slightly scale the y velocity to speed up the game, capping at +- 150
            if math.abs(self.ball.dy) < 150 then
                self.ball.dy = self.ball.dy * 1.02
            end

            -- only allow colliding with one brick, for corners
            break
        end
		
		-- only check collision if we're in play
        if brick.inPlay and self.ball2:collides(brick) then
		
			-- set the brick to unlocked if key powerup obtained
			if self.keyObtained == true then
				brick.unlocked = true
			end

            -- add to score (unless locked), bonus points if unlocked brick destroyed
			if not (brick.locked == true and brick.unlocked == false) then
				if brick.locked == true and brick.unlocked == true then
					self.score = self.score + 2000
					self.paddleScore = self.paddleScore + 2000
				else
					self.score = self.score + (brick.tier * 200 + brick.color * 25)
					self.paddleScore = self.paddleScore + (brick.tier * 200 + brick.color * 25)
				end
			end

            -- trigger the brick's hit function, which removes it from play
            brick:hit()

            -- if we have enough points, recover a point of health
            if self.score > self.recoverPoints then
                -- can't go above 3 health
                self.health = math.min(3, self.health + 1)

                -- multiply recover points by 2
                self.recoverPoints = math.min(100000, self.recoverPoints * 2)

                -- play recover sound effect
                gSounds['recover']:play()
            end

            -- go to our victory screen if there are no more bricks left
            if self:checkVictory() then
                gSounds['victory']:play()

                gStateMachine:change('victory', {
                    level = self.level,
                    paddle = self.paddle,
                    health = self.health,
                    score = self.score,
                    highScores = self.highScores,
                    ball = self.ball,
                    recoverPoints = self.recoverPoints
                })
            end

            --
            -- collision code for bricks
            --
            -- we check to see if the opposite side of our velocity is outside of the brick;
            -- if it is, we trigger a collision on that side. else we're within the X + width of
            -- the brick and should check to see if the top or bottom edge is outside of the brick,
            -- colliding on the top or bottom accordingly 
            --

            -- left edge; only check if we're moving right, and offset the check by a couple of pixels
            -- so that flush corner hits register as Y flips, not X flips
            if self.ball2.x + 2 < brick.x and self.ball2.dx > 0 then
                
                -- flip x velocity and reset position outside of brick
                self.ball2.dx = -self.ball2.dx
                self.ball2.x = brick.x - 8
            
            -- right edge; only check if we're moving left, , and offset the check by a couple of pixels
            -- so that flush corner hits register as Y flips, not X flips
            elseif self.ball2.x + 6 > brick.x + brick.width and self.ball2.dx < 0 then
                
                -- flip x velocity and reset position outside of brick
                self.ball2.dx = -self.ball2.dx
                self.ball2.x = brick.x + 32
            
            -- top edge if no X collisions, always check
            elseif self.ball2.y < brick.y then
                
                -- flip y velocity and reset position outside of brick
                self.ball2.dy = -self.ball2.dy
                self.ball2.y = brick.y - 8
            
            -- bottom edge if no X collisions or top collision, last possibility
            else
                
                -- flip y velocity and reset position outside of brick
                self.ball2.dy = -self.ball2.dy
                self.ball2.y = brick.y + 16
            end

            -- slightly scale the y velocity to speed up the game, capping at +- 150
            if math.abs(self.ball2.dy) < 150 then
                self.ball2.dy = self.ball2.dy * 1.02
            end

            -- only allow colliding with one brick, for corners
            break
        end
		
		-- only check collision if we're in play
        if brick.inPlay and self.ball3:collides(brick) then
		
			-- set the brick to unlocked if key powerup obtained
			if self.keyObtained == true then
				brick.unlocked = true
			end

            -- add to score (unless locked), bonus points if unlocked brick destroyed
			if not (brick.locked == true and brick.unlocked == false) then
				if brick.locked == true and brick.unlocked == true then
					self.score = self.score + 2000
					self.paddleScore = self.paddleScore + 2000
				else
					self.score = self.score + (brick.tier * 200 + brick.color * 25)
					self.paddleScore = self.paddleScore + (brick.tier * 200 + brick.color * 25)
				end
			end

            -- trigger the brick's hit function, which removes it from play
            brick:hit()

            -- if we have enough points, recover a point of health
            if self.score > self.recoverPoints then
                -- can't go above 3 health
                self.health = math.min(3, self.health + 1)

                -- multiply recover points by 2
                self.recoverPoints = math.min(100000, self.recoverPoints * 2)

                -- play recover sound effect
                gSounds['recover']:play()
            end

            -- go to our victory screen if there are no more bricks left
            if self:checkVictory() then
                gSounds['victory']:play()

                gStateMachine:change('victory', {
                    level = self.level,
                    paddle = self.paddle,
                    health = self.health,
                    score = self.score,
                    highScores = self.highScores,
                    ball = self.ball,
                    recoverPoints = self.recoverPoints
                })
            end

            --
            -- collision code for bricks
            --
            -- we check to see if the opposite side of our velocity is outside of the brick;
            -- if it is, we trigger a collision on that side. else we're within the X + width of
            -- the brick and should check to see if the top or bottom edge is outside of the brick,
            -- colliding on the top or bottom accordingly 
            --

            -- left edge; only check if we're moving right, and offset the check by a couple of pixels
            -- so that flush corner hits register as Y flips, not X flips
            if self.ball3.x + 2 < brick.x and self.ball3.dx > 0 then
                
                -- flip x velocity and reset position outside of brick
                self.ball3.dx = -self.ball3.dx
                self.ball3.x = brick.x - 8
            
            -- right edge; only check if we're moving left, , and offset the check by a couple of pixels
            -- so that flush corner hits register as Y flips, not X flips
            elseif self.ball3.x + 6 > brick.x + brick.width and self.ball3.dx < 0 then
                
                -- flip x velocity and reset position outside of brick
                self.ball3.dx = -self.ball3.dx
                self.ball3.x = brick.x + 32
            
            -- top edge if no X collisions, always check
            elseif self.ball3.y < brick.y then
                
                -- flip y velocity and reset position outside of brick
                self.ball3.dy = -self.ball3.dy
                self.ball3.y = brick.y - 8
            
            -- bottom edge if no X collisions or top collision, last possibility
            else
                
                -- flip y velocity and reset position outside of brick
                self.ball3.dy = -self.ball3.dy
                self.ball3.y = brick.y + 16
            end

            -- slightly scale the y velocity to speed up the game, capping at +- 150
            if math.abs(self.ball3.dy) < 150 then
                self.ball3.dy = self.ball3.dy * 1.02
            end

            -- only allow colliding with one brick, for corners
            break
        end
    end
	
	-- spawn powerups after random intervals of time
	self.timer = self.timer + dt
	self.multiball:update(dt)
	self.key:update(dt)
	if self.timer > self.multiballTime then
		self.multiball.moving = true
	end
	if self.timer > self.keyTime and self.level % 5 == 0 then
		self.key.moving = true
	end
	
	-- check collisions for multiball colliding with paddle
	if self.multiball:collides(self.paddle) then
		gSounds['high-score']:play()
		self.multiball.y = VIRTUAL_HEIGHT + self.multiball.height
		self.multiball.moving = false
		
		-- add new balls into play
		self.ball2.x = self.ball.x
		self.ball2.y = self.ball.y
		self.ball2.dx = math.random(-200, 200)
		self.ball2.dy = math.random(-40, -90)
		
		self.ball3.x = self.ball.x
		self.ball3.y = self.ball.y
		self.ball3.dx = math.random(-200, 200)
		self.ball3.dy = math.random(-40, -90)
	end
	
	-- check collisions for key colliding with paddle
	if self.key:collides(self.paddle) then
		gSounds['high-score']:play()
		self.key.y = VIRTUAL_HEIGHT + self.key.height
		self.key.moving = false
		self.keyObtained = true
	end
	
	-- if ball goes off screen, freeze it to avoid wall-bounce noises
	if self.ball.y >= VIRTUAL_HEIGHT then
		self.ball.x = VIRTUAL_WIDTH / 2
		self.ball.y = VIRTUAL_HEIGHT
		self.ball.dx = 0
		self.ball.dy = 0
	end
	if self.ball2.y >= VIRTUAL_HEIGHT then
		self.ball2.x = VIRTUAL_WIDTH / 2
		self.ball2.y = VIRTUAL_HEIGHT
		self.ball2.dx = 0
		self.ball2.dy = 0
	end
	if self.ball3.y >= VIRTUAL_HEIGHT then
		self.ball3.x = VIRTUAL_WIDTH / 2
		self.ball3.y = VIRTUAL_HEIGHT
		self.ball3.dx = 0
		self.ball3.dy = 0
	end

    -- if balls goes below bounds, revert to serve state and decrease health
    if self.ball.y >= VIRTUAL_HEIGHT and self.ball2.y >= VIRTUAL_HEIGHT and self.ball3.y >= VIRTUAL_HEIGHT then
        self.health = self.health - 1
        gSounds['hurt']:play()
		
		self.paddle:change(-1)

        if self.health == 0 then
            gStateMachine:change('game-over', {
                score = self.score,
                highScores = self.highScores
            })
        else
            gStateMachine:change('serve', {
                paddle = self.paddle,
                bricks = self.bricks,
                health = self.health,
                score = self.score,
                highScores = self.highScores,
                level = self.level,
                recoverPoints = self.recoverPoints
            })
        end
    end
	
	-- if score goes above a certain threshold, paddle size increases
	if self.paddleScore >= self.paddleThreshold then
		self.paddle:change(1)
		self.paddleScore = 0
		self.paddleThreshold = self.paddleThreshold * 2
		gSounds['increase']:play()
	end

    -- for rendering particle systems
    for k, brick in pairs(self.bricks) do
        brick:update(dt)
    end

	-- to exit the game
    if love.keyboard.wasPressed('escape') then
        love.event.quit()
    end
end

function PlayState:render()
    -- render bricks
    for k, brick in pairs(self.bricks) do
        brick:render()
    end

    -- render all particle systems
    for k, brick in pairs(self.bricks) do
        brick:renderParticles()
    end

    self.paddle:render()
    self.ball:render()
	self.ball2:render()
	self.ball3:render()

    renderScore(self.score)
    renderHealth(self.health)
	
	self.multiball:render()
	self.key:render()

    -- pause text, if paused
    if self.paused then
        love.graphics.setFont(gFonts['large'])
        love.graphics.printf("PAUSED", 0, VIRTUAL_HEIGHT / 2 - 16, VIRTUAL_WIDTH, 'center')
    end
end

function PlayState:checkVictory()
    for k, brick in pairs(self.bricks) do
        if brick.inPlay and brick.locked == false then
            return false
        end 
    end

    return true
end